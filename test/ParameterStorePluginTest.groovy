import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasItem
import static org.hamcrest.Matchers.instanceOf
import static org.mockito.Mockito.any
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.eq
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.spy
import static org.mockito.Mockito.thenReturn
import static org.mockito.Mockito.times
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ParameterStorePluginTest {
    @Nested
    public class Init {
        @BeforeEach
        @AfterEach
        public void reset() {
            StagePlugins.reset()
        }

        @Test
        void addsPluginToTheDeployStage() {
            ParameterStorePlugin.init()

            def plugins = StagePlugins.getPluginsFor(mock(DeployStage.class))

            assertThat(plugins, hasItem(instanceOf(ParameterStorePlugin.class)))
        }
    }

    @Nested
    public class Apply {
        @Test
        void decoratesDeployStageWithParameterStoreClosure() {
            def expectedEnvironment = 'foo'
            def expectedDecoration = { }
            def plugin = spy(new ParameterStorePlugin())
            doReturn(expectedDecoration).when(plugin).parameterStoreClosure(expectedEnvironment)
            def stage = mock(DeployStage)
            when(stage.getEnvironment()).thenReturn(expectedEnvironment)

            plugin.apply(stage)

            verify(stage).decorate(expectedDecoration)
        }

        @Test
        void doesNothingIfNotDeployStage() {
            def plugin = new ParameterStorePlugin()
            def stage = mock(Stage)

            plugin.apply(stage)

            verify(stage, times(0)).decorate(any(Closure))
        }
    }

    @Nested
    public class ParameterStoreClosure {
        @Test
        void runsTheInnerClosure() {
            def wasCalled = false
            def innerClosure = { wasCalled = true }
            def plugin = new ParameterStorePlugin()
            def parameterStoreClosure = plugin.parameterStoreClosure('foo')

            parameterStoreClosure.delegate = new MockWorkflowScript()
            parameterStoreClosure(innerClosure)

            assertThat(wasCalled, equalTo(true))
        }

        @Test
        void callsWithAwsParameterStore() {
            def expectedEnvironment = 'foo'
            def workflowScript = spy(new MockWorkflowScript())
            def expectedParameters = [:]
            def plugin = spy(new ParameterStorePlugin())
            doReturn(expectedParameters).when(plugin).getParameters(eq(expectedEnvironment), any(ScmUtil))
            def parameterStoreClosure = plugin.parameterStoreClosure(expectedEnvironment)
            def innerClosure = { }

            parameterStoreClosure.delegate = workflowScript
            parameterStoreClosure(innerClosure)

            verify(workflowScript).withAWSParameterStore(expectedParameters, innerClosure)
        }
    }

    @Nested
    public class GetParameters {
        @Test
        void usesBasenameNamingByDefault() {
            def plugin = new ParameterStorePlugin()

            def results = plugin.getParameters('foo', mock(ScmUtil))

            assertThat(results['naming'], equalTo('basename'))
        }

        @Test
        void constructsPathUsingOrgRepoEnvironment() {
            def expectedOrg = 'Org'
            def expectedRepo = 'Repo'
            def plugin = new ParameterStorePlugin()
            def scmUtil = mock(ScmUtil)
            when(scmUtil.getParsedUrl()).thenReturn([
                org: expectedOrg,
                repo: expectedRepo
            ])

            def results = plugin.getParameters('foo', scmUtil)

            assertThat(results['path'], equalTo("/${expectedOrg}/${expectedRepo}/foo"))
        }
    }
}
