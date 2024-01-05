package com.redwoods.codegen;

import com.redwoods.codegen.config.ApplicationConfig;
import org.springframework.stereotype.Component;

@Component
public class ServiceImplClassGenerator {

    private ApplicationConfig applicationConfig;
    private CodeWriter codeWriter;

    public ServiceImplClassGenerator(ApplicationConfig applicationConfig, CodeWriter codeWriter){
        this.applicationConfig = applicationConfig;
        this.codeWriter = codeWriter;
    }

    public void generateServiceImplClass(String entityName){

        String basePackage = applicationConfig.getPackageName();
        String serviceInterface = entityName+"Service";
        String serviceImplementation = entityName+"ServiceImpl";

        String content = String.format("package %s.service.impl;\n\n" +
                        "import java.util.ArrayList;\n" +
                        "import java.util.List;\n" +
                        "import java.util.Optional;\n\n" +
                        "import org.modelmapper.ModelMapper;\n" +
                        "import org.slf4j.Logger;\n" +
                        "import org.slf4j.LoggerFactory;\n" +
                        "import org.springframework.stereotype.Service;\n\n" +
                        "import %s.dtos.TimelineResponseDto;\n" +
                        "import %s.dtos.TimelineRequestDto;\n" +
                        "import %s.exceptions.NotFoundException;\n" +
                        "import %s.models.Address;\n" +
                        "import %s.models.Timeline;\n" +
                        "import %s.models.TimelineContact;\n" +
                        "import %s.models.TimelineData;\n" +
                        "import %s.repos.TimelineRepository;\n" +
                        "import %s.service.%s;\n\n" +
                        "@Service\n" +
                        "public class %s implements %s {\n\n" +
                        "   private TimelineRepository TimelineRepository;\n" +
                        "   private ModelMapper modelMapper;\n" +
                        "    private static final Logger LOGGER = LoggerFactory.getLogger(%s.class);\n\n" +
                        "    public %s(TimelineRepository TimelineRepository, ModelMapper modelMapper) {\n" +
                        "        this.TimelineRepository = TimelineRepository;\n" +
                        "        this.modelMapper = modelMapper;\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    public TimelineResponseDto getTimeline(Long TimelineId) throws NotFoundException {\n" +
                        "        try {\n" +
                        "            Optional<Timeline> optionalTimeline = TimelineRepository.findById(TimelineId);\n" +
                        "            if (optionalTimeline.isEmpty()) {\n" +
                        "                throw new NotFoundException(\"Timeline Doesn't exist.\");\n" +
                        "            }\n\n" +
                        "            return modelMapper.map(optionalTimeline.get(), TimelineResponseDto.class);\n" +
                        "        } catch (Exception ex) {\n" +
                        "            LOGGER.error(\"Error occurred while processing getTimeline\", ex);\n" +
                        "            throw new RuntimeException(\"Error occurred while processing getTimeline\", ex);\n" +
                        "        }\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    public List<TimelineResponseDto> getTimelines() {\n" +
                        "        try {\n" +
                        "            List<Timeline> Timelines = TimelineRepository.findAll();\n" +
                        "            List<TimelineResponseDto> TimelineResponseDtos = null;\n" +
                        "            if (!Timelines.isEmpty()) {\n" +
                        "                TimelineResponseDtos = new ArrayList<>();\n" +
                        "                for (Timeline Timeline : Timelines) {\n" +
                        "                    TimelineResponseDtos.add(modelMapper.map(Timeline, TimelineResponseDto.class));\n" +
                        "                }\n" +
                        "            }\n" +
                        "            return TimelineResponseDtos;\n" +
                        "        } catch (Exception ex) {\n" +
                        "            LOGGER.error(\"Error occurred while processing getTimelines\", ex);\n" +
                        "            throw new RuntimeException(\"Error occurred while processing getTimelines\", ex);\n" +
                        "        }\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    public String deleteTimeline(Long TimelineId) {\n" +
                        "        if (TimelineRepository.findById(TimelineId).isPresent()) {\n" +
                        "            TimelineRepository.deleteById(TimelineId);\n" +
                        "            return \"Timeline Deleted Successfully!!\";\n" +
                        "        }\n" +
                        "        return \"No Timeline exists in the database with the provided id.\";\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    public TimelineResponseDto updateTimeline(Long TimelineId, TimelineRequestDto TimelineRequestDto) {\n" +
                        "        try {\n" +
                        "            Optional<Timeline> optionalTimeline = TimelineRepository.findById(TimelineId);\n" +
                        "            if (optionalTimeline.isPresent()) {\n" +
                        "                Timeline Timeline = optionalTimeline.get();\n" +
                        "                // Update Timeline fields based on TimelineRequestDto\n\n" +
                        "                if (TimelineRequestDto.getTimelineContact() != null) {\n" +
                        "                    TimelineContact TimelineContact = modelMapper.map(TimelineRequestDto.getTimelineContact(), TimelineContact.class);\n" +
                        "                    Timeline.setTimelineContact(TimelineContact);\n" +
                        "                }\n\n" +
                        "                // update address\n" +
                        "                if (TimelineRequestDto.getAddress() != null) {\n" +
                        "                    List<Address> addressList = List.of(modelMapper.map(TimelineRequestDto.getAddress(), Address[].class));\n" +
                        "                    Timeline.setAddress(addressList);\n" +
                        "                }\n\n" +
                        "                // update Timeline data\n" +
                        "                if (TimelineRequestDto.getTimelineData() != null) {\n" +
                        "                    List<TimelineData> TimelineDataList = List.of(modelMapper.map(TimelineRequestDto.getTimelineData(), TimelineData[].class));\n" +
                        "                    Timeline.setTimelineData(TimelineDataList);\n" +
                        "                }\n\n" +
                        "                // Update other fields\n" +
                        "                // ...\n\n" +
                        "                Timeline.setLast_updated_by(\"admin\");\n" +
                        "                Timeline.setLast_updated_on(System.currentTimeMillis());\n\n" +
                        "                return modelMapper.map(TimelineRepository.save(Timeline), TimelineResponseDto.class);\n" +
                        "            } else {\n" +
                        "                return null;\n" +
                        "            }\n" +
                        "        } catch (Exception ex) {\n" +
                        "            LOGGER.error(\"Error occurred while processing updateTimelines\", ex);\n" +
                        "            throw new RuntimeException(\"Error occurred while processing updateTimelines\", ex);\n" +
                        "        }\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    public Long addTimeline(Timeline Timeline) {\n" +
                        "        try {\n" +
                        "            Timeline newTimeline = TimelineRepository.save(Timeline);\n" +
                        "            return newTimeline.getId();\n" +
                        "        } catch (Exception ex) {\n" +
                        "            LOGGER.error(\"Error occurred while processing addTimelines\", ex);\n" +
                        "            throw new RuntimeException(\"Error occurred while processing addTimelines\", ex);\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n",
                basePackage, basePackage, basePackage, basePackage, basePackage, basePackage, basePackage, basePackage,
                basePackage, serviceInterface, serviceImplementation, serviceImplementation, serviceImplementation, serviceImplementation, serviceImplementation);

        codeWriter.writeToFile(entityName+"ServiceImpl" + ".java", content);
    }
}
