package es.lolrav.buildkiteclient.model.request


interface CreatePipelineStep {
    val type: String
    fun <T> visit(stepMapper: StepMapper<T>): T
}

interface StepMapper<T> {
    fun process(commandStep: CommandStep): T
    fun process(waitStep: WaitStep): T
    fun process(blockStep: BlockStep): T
    fun process(triggerStep: TriggerStep): T
}

data class CommandStep(
    val name: String,
    val command: String,
    val artifactPaths: String? = null,
    val branchConfiguration: String? = null,
    val env: Map<String, Any?>? = null,
    val timeoutInMinutes: Int? = null,
    val agentQueryRules: List<String>? = null
) : CreatePipelineStep {
    override val type: String = "script"
    override fun <T> visit(stepMapper: StepMapper<T>): T = stepMapper.process(this)
}

data class WaitStep(
    val continueOnFailure: Boolean? = null
) : CreatePipelineStep {
    override val type: String = "waiter"
    override fun <T> visit(stepMapper: StepMapper<T>): T = stepMapper.process(this)
}

data class BlockStep(
    val prompt: String? = null,
    val fields: List<Input>? = null,
    val branches: String? = null
) : CreatePipelineStep {
    override val type: String = "manual"
    override fun <T> visit(stepMapper: StepMapper<T>): T = stepMapper.process(this)

    interface InputMapper<T> {
        fun process(textInput: TextInput): T
        fun process(selectInput: SelectInput): T
    }

    interface Input {
        fun <T> visit(mapper: InputMapper<T>): T
    }

    data class TextInput(
        val text: String,
        val key: String,
        val hint: String? = null,
        val required: Boolean? = null,
        val default: String? = null
    ) : Input {
        override fun <T> visit(mapper: InputMapper<T>): T = mapper.process(this)
    }

    data class SelectInput(
        val select: String,
        val key: String,
        val options: List<SelectOption>,
        val hint: String? = null,
        val required: Boolean? = null,
        val multiple: Boolean? = null,
        val default: String? = null
    ) : Input {
        override fun <T> visit(mapper: InputMapper<T>): T = mapper.process(this)
    }

    data class SelectOption(
        val label: String,
        val value: String)
}


data class TriggerStep(
    val trigger: String,
    val triggerProjectSlug: String,
    val build: BuildOptions? = null,
    val label: String? = null,
    val async: Boolean? = null,
    val branches: String? = null
) : CreatePipelineStep {
    override val type: String = "trigger"
    override fun <T> visit(stepMapper: StepMapper<T>): T = stepMapper.process(this)

    data class BuildOptions(
        val message: String? = null,
        val commit: String? = null,
        val branch: String? = null,
        val metaData: Map<String, String?>? = null,
        val env: Map<String, String?>? = null
    )
}

