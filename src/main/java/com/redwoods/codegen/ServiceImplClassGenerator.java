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
                        "import %s.dtos.UserResponseDto;\n" +
                        "import %s.dtos.UserRequestDto;\n" +
                        "import %s.exceptions.NotFoundException;\n" +
                        "import %s.models.Address;\n" +
                        "import %s.models.User;\n" +
                        "import %s.models.UserContact;\n" +
                        "import %s.models.UserData;\n" +
                        "import %s.repos.UserRepository;\n" +
                        "import %s.service.%s;\n\n" +
                        "@Service\n" +
                        "public class %s implements %s {\n\n" +
                        "   private UserRepository UserRepository;\n" +
                        "   private ModelMapper modelMapper;\n" +
                        "    private static final Logger LOGGER = LoggerFactory.getLogger(%s.class);\n\n" +
                        "    public %s(UserRepository UserRepository, ModelMapper modelMapper) {\n" +
                        "        this.UserRepository = UserRepository;\n" +
                        "        this.modelMapper = modelMapper;\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    public UserResponseDto getUser(Long UserId) throws NotFoundException {\n" +
                        "        try {\n" +
                        "            Optional<User> optionalUser = UserRepository.findById(UserId);\n" +
                        "            if (optionalUser.isEmpty()) {\n" +
                        "                throw new NotFoundException(\"User Doesn't exist.\");\n" +
                        "            }\n\n" +
                        "            return modelMapper.map(optionalUser.get(), UserResponseDto.class);\n" +
                        "        } catch (Exception ex) {\n" +
                        "            LOGGER.error(\"Error occurred while processing getUser\", ex);\n" +
                        "            throw new RuntimeException(\"Error occurred while processing getUser\", ex);\n" +
                        "        }\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    public List<UserResponseDto> getUsers() {\n" +
                        "        try {\n" +
                        "            List<User> Users = UserRepository.findAll();\n" +
                        "            List<UserResponseDto> UserResponseDtos = null;\n" +
                        "            if (!Users.isEmpty()) {\n" +
                        "                UserResponseDtos = new ArrayList<>();\n" +
                        "                for (User User : Users) {\n" +
                        "                    UserResponseDtos.add(modelMapper.map(User, UserResponseDto.class));\n" +
                        "                }\n" +
                        "            }\n" +
                        "            return UserResponseDtos;\n" +
                        "        } catch (Exception ex) {\n" +
                        "            LOGGER.error(\"Error occurred while processing getUsers\", ex);\n" +
                        "            throw new RuntimeException(\"Error occurred while processing getUsers\", ex);\n" +
                        "        }\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    public String deleteUser(Long UserId) {\n" +
                        "        if (UserRepository.findById(UserId).isPresent()) {\n" +
                        "            UserRepository.deleteById(UserId);\n" +
                        "            return \"User Deleted Successfully!!\";\n" +
                        "        }\n" +
                        "        return \"No User exists in the database with the provided id.\";\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    public UserResponseDto updateUser(Long UserId, UserRequestDto UserRequestDto) {\n" +
                        "        try {\n" +
                        "            Optional<User> optionalUser = UserRepository.findById(UserId);\n" +
                        "            if (optionalUser.isPresent()) {\n" +
                        "                User User = optionalUser.get();\n" +
                        "                // Update User fields based on UserRequestDto\n\n" +
                        "                if (UserRequestDto.getUserContact() != null) {\n" +
                        "                    UserContact UserContact = modelMapper.map(UserRequestDto.getUserContact(), UserContact.class);\n" +
                        "                    User.setUserContact(UserContact);\n" +
                        "                }\n\n" +
                        "                // update address\n" +
                        "                if (UserRequestDto.getAddress() != null) {\n" +
                        "                    List<Address> addressList = List.of(modelMapper.map(UserRequestDto.getAddress(), Address[].class));\n" +
                        "                    User.setAddress(addressList);\n" +
                        "                }\n\n" +
                        "                // update User data\n" +
                        "                if (UserRequestDto.getUserData() != null) {\n" +
                        "                    List<UserData> UserDataList = List.of(modelMapper.map(UserRequestDto.getUserData(), UserData[].class));\n" +
                        "                    User.setUserData(UserDataList);\n" +
                        "                }\n\n" +
                        "                // Update other fields\n" +
                        "                // ...\n\n" +
                        "                User.setLast_updated_by(\"admin\");\n" +
                        "                User.setLast_updated_on(System.currentTimeMillis());\n\n" +
                        "                return modelMapper.map(UserRepository.save(User), UserResponseDto.class);\n" +
                        "            } else {\n" +
                        "                return null;\n" +
                        "            }\n" +
                        "        } catch (Exception ex) {\n" +
                        "            LOGGER.error(\"Error occurred while processing updateUsers\", ex);\n" +
                        "            throw new RuntimeException(\"Error occurred while processing updateUsers\", ex);\n" +
                        "        }\n" +
                        "    }\n\n" +
                        "    @Override\n" +
                        "    public Long addUser(User User) {\n" +
                        "        try {\n" +
                        "            User newUser = UserRepository.save(User);\n" +
                        "            return newUser.getId();\n" +
                        "        } catch (Exception ex) {\n" +
                        "            LOGGER.error(\"Error occurred while processing addUsers\", ex);\n" +
                        "            throw new RuntimeException(\"Error occurred while processing addUsers\", ex);\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n",
                basePackage, basePackage, basePackage, basePackage, basePackage, basePackage, basePackage, basePackage,
                basePackage, serviceInterface, serviceImplementation, serviceImplementation, serviceImplementation, serviceImplementation, serviceImplementation);

        codeWriter.writeToFile(entityName+"ServiceImpl" + ".java", content);
    }
}
