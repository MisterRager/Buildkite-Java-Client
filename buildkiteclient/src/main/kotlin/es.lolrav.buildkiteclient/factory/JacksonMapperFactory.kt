package es.lolrav.buildkiteclient.factory

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.mrbean.MrBeanModule
import es.lolrav.buildkiteclient.json.request.BlockStepInputSerializer
import es.lolrav.buildkiteclient.json.request.StepSerializer

class JacksonMapperFactory {
    private val buildkiteModule: SimpleModule by lazy {
        SimpleModule(
            "BuildkiteModule",
            Version(
                0, 0, 1,
                null,
                "es.lolrav", "buildkiteclient")
        ).apply {
            addSerializer(StepSerializer())
            addSerializer(BlockStepInputSerializer())
        }
    }

    val mapper: ObjectMapper by lazy {
        ObjectMapper().apply {
            registerModule(MrBeanModule())
            propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
            registerModule(buildkiteModule)
            setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
    }
}