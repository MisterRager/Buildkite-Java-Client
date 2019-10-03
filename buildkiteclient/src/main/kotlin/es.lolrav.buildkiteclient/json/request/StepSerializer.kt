package es.lolrav.buildkiteclient.json.request

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import es.lolrav.buildkiteclient.model.request.*

class StepSerializer : StdSerializer<CreatePipelineStep>(CreatePipelineStep::class.java) {
    override fun serialize(value: CreatePipelineStep, gen: JsonGenerator, provider: SerializerProvider) =
        value.visit(Processor(gen))

    private class Processor(
        private val json: JsonGenerator
    ) : StepMapper<Unit> {
        override fun process(commandStep: CommandStep) {
            json.writeObject(object {
                val name: String = commandStep.name
                val command: String = commandStep.command
                val artifactPaths: String? = commandStep.artifactPaths
                val branchConfiguration: String? = commandStep.branchConfiguration
                val env: Map<String, Any?>? = commandStep.env
                val timeoutInMinutes: Int? = commandStep.timeoutInMinutes
                val agentQueryRules: List<String>? = commandStep.agentQueryRules
            })
        }

        override fun process(waitStep: WaitStep): Unit = json.writeObject(waitStep)
        override fun process(blockStep: BlockStep): Unit = json.writeObject(blockStep)
        override fun process(triggerStep: TriggerStep): Unit = json.writeObject(triggerStep)
    }
}