package es.lolrav.buildkiteclient.json.request

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import es.lolrav.buildkiteclient.model.request.BlockStep

class BlockStepInputSerializer : StdSerializer<BlockStep.Input>(BlockStep.Input::class.java) {
    override fun serialize(value: BlockStep.Input, gen: JsonGenerator, provider: SerializerProvider) =
        value.visit(Processor(gen))

    private class Processor(private val json: JsonGenerator) : BlockStep.InputMapper<Unit> {
        override fun process(textInput: BlockStep.TextInput): Unit = json.writeObject(textInput)
        override fun process(selectInput: BlockStep.SelectInput): Unit = json.writeObject(selectInput)
    }
}