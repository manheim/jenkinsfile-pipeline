import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasItem
import static org.hamcrest.Matchers.instanceOf
import static org.mockito.Mockito.any
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.eq
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.verify

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class WithAwsPluginTest {
    @BeforeEach
    @AfterEach
    public void reset() {
        StagePlugins.reset()
    }

    @Nested
    public class WithRole {
        @Test
        void isFluent() {
            def result = WithAwsPlugin.withRole()

            assertThat(result, equalTo(WithAwsPlugin.class))
        }
    }

    @Nested
    public class Init {
        @Test
        void addsPluginToTheDeployStage() {
            WithAwsPlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(DeployStage))

            assertThat(plugins, hasItem(instanceOf(WithAwsPlugin)))
        }
    }

    @Nested
    public class Apply {
        @Test
        void decoratesDeployStageWithAws() {
            def expectedDecoration = { }
            def plugin = spy(new WithAwsPlugin())
            doReturn(expectedDecoration).when(plugin).withAwsClosure()
            def stage = mock(DeployStage)

            plugin.apply(stage)

            verify(stage).decorate(expectedDecoration)
        }
    }

    @Nested
    public class WithAwsClosure {
        @Test
        void callsInnerClosure() {
            def wasCalled = false
            def innerClosure = { wasCalled = true }
            def plugin = spy(new WithAwsPlugin())

            def withAwsClosure = plugin.withAwsClosure('foo')
            withAwsClosure.delegate = new MockWorkflowScript()
            withAwsClosure(innerClosure)

            assertThat(wasCalled, equalTo(true))
        }

        @Test
        void callsWithAws() {
            def expectedOptions = [ option: 'someValue' ]
            def workflowScript = spy(new MockWorkflowScript())
            def plugin = spy(new WithAwsPlugin())
            doReturn(expectedOptions).when(plugin).getOptions(any(String), any(EnvironmentUtil))

            def withAwsClosure = plugin.withAwsClosure()
            withAwsClosure.delegate = workflowScript
            withAwsClosure { }

            verify(workflowScript).withAWS(eq(expectedOptions), any(Closure))
        }
    }

    @Nested
    public class GetOptions {
        @Test
        void returnsEmptyMapByDefault() {
            def plugin = new WithAwsPlugin()

            def results = plugin.getOptions('foo', new EnvironmentUtil())

            assertThat(results, equalTo([:]))
        }

        @Test
        void returnsDefaultRoleIfEnvironmentVariablePresent() {
            def expectedRole = 'myRole'
            def plugin = new WithAwsPlugin()
            def environmentUtil = spy(new EnvironmentUtil())
            doReturn(expectedRole).when(environmentUtil).getEnvironmentVariable('AWS_ROLE_ARN')

            def results = plugin.getOptions('foo', environmentUtil)

            assertThat(results, equalTo(iamRole: expectedRole))
        }

        @Test
        void returnsEnvironmentSpecificRoleIfEnvironmentVariablePresent() {
            def environment = 'qa'
            def expectedRole = 'qaRole'
            def plugin = new WithAwsPlugin()
            def environmentUtil = spy(new EnvironmentUtil())
            doReturn(expectedRole).when(environmentUtil).getEnvironmentVariable("${environment.toUpperCase()}_AWS_ROLE_ARN".toString())

            def results = plugin.getOptions(environment, environmentUtil)

            assertThat(results, equalTo(iamRole: expectedRole))
        }

        @Test
        void prefersEnvironmentSpecificRoleOverDefaultRole() {
            def environment = 'qa'
            def expectedRole = 'qaRole'
            def defaultRole = 'defaultRole'
            def plugin = new WithAwsPlugin()
            def environmentUtil = spy(new EnvironmentUtil())
            doReturn(defaultRole).when(environmentUtil).getEnvironmentVariable("AWS_ROLE_ARN".toString())
            doReturn(expectedRole).when(environmentUtil).getEnvironmentVariable("${environment.toUpperCase()}_AWS_ROLE_ARN".toString())

            def results = plugin.getOptions(environment, environmentUtil)

            assertThat(results, equalTo(iamRole: expectedRole))
        }
    }
}
