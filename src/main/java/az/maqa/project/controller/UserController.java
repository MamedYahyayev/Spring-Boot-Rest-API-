package az.maqa.project.controller;

import org.springframework.hateoas.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import az.maqa.project.exceptions.UserServiceExceptions;
import az.maqa.project.model.request.PasswordResetModel;
import az.maqa.project.model.request.PasswordResetRequestModel;
import az.maqa.project.model.request.UserDetailsRequestModel;
import az.maqa.project.model.response.AddressesRest;
import az.maqa.project.model.response.ErrorMessages;
import az.maqa.project.model.response.OperationStatusModel;
import az.maqa.project.model.response.RequestOperationName;
import az.maqa.project.model.response.UserRest;
import az.maqa.project.service.AddressService;
import az.maqa.project.service.UserService;
import az.maqa.project.shared.dto.AddressDto;
import az.maqa.project.shared.dto.UserDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/users")
//@CrossOrigin(origins = {"http://localhost:8083" , "http://localhost:8084" })  
// this statement allow us these localhosts communicate our all UserController class urls
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AddressService addressService;

	@ApiOperation(value = "The Get User Details Web Service Endpoint", notes = Constants.API_OPERATION_NOTE)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "authorization", value = Constants.API_DESCRIPTION, paramType = "header") })
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public UserRest getUser(@PathVariable String id) {
		UserRest returnValue = new UserRest();

		ModelMapper modelMapper = new ModelMapper();

		UserDto user = userService.getUserByUserId(id);
		returnValue = modelMapper.map(user, UserRest.class);

		return returnValue;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		UserRest returnValue = new UserRest();

		if (userDetails.getFirstName().isEmpty())
			throw new UserServiceExceptions(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		ModelMapper mapper = new ModelMapper();
		UserDto userDto = mapper.map(userDetails, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);
		returnValue = mapper.map(createdUser, UserRest.class);

		return returnValue;

	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "authorization", value = Constants.API_DESCRIPTION, paramType = "header") })
	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {

		UserRest returnValue = new UserRest();

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);

		UserDto updatedUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);

		return returnValue;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "authorization", value = Constants.API_DESCRIPTION, paramType = "header") })
	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());

		userService.deleteUser(id);

		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "authorization", value = Constants.API_DESCRIPTION, paramType = "header") })
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		List<UserRest> returnValue = new ArrayList<>();

		List<UserDto> users = userService.getUsers(page, limit);

		for (UserDto userDto : users) {
			UserRest userRest = new UserRest();
			BeanUtils.copyProperties(userDto, userRest);
			returnValue.add(userRest);
		}

		return returnValue;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "authorization", value = Constants.API_DESCRIPTION, paramType = "header") })
	@GetMapping(path = "/{id}/addresses", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE, "application/hal+json" })
	public CollectionModel<AddressesRest> getUserAddresses(@PathVariable String id) {
		List<AddressesRest> addressesListRestModel = new ArrayList<>();
		CollectionModel<AddressesRest> result = null;
		ModelMapper modelMapper = new ModelMapper();

		List<AddressDto> addressDto = addressService.getAddresses(id);

		if (addressDto != null && !addressDto.isEmpty()) {
			Type listType = new TypeToken<List<AddressesRest>>() {
			}.getType();
			addressesListRestModel = modelMapper.map(addressDto, listType);

			for (AddressesRest addressRest : addressesListRestModel) {

				Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(id, addressRest.getAddressId()))
						.withRel("addresses");
				addressRest.add(addressLink);

				Link userLink = linkTo(UserController.class).slash(id).withRel("user");
				addressRest.add(userLink);

				result = new CollectionModel<AddressesRest>(addressesListRestModel, addressLink, userLink);
			}

		}

		return result;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "authorization", value = Constants.API_DESCRIPTION, paramType = "header") })
	@GetMapping(path = "/{id}/addresses/{addressId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE, "application/hal+json" })
	public AddressesRest getUserAddress(@PathVariable String id, @PathVariable String addressId) {
		AddressDto addressDto = addressService.getAddress(addressId);

		ModelMapper modelMapper = new ModelMapper();

		UserDto user = userService.getUserByUserId(id);
		if (user == null)
			throw new UserServiceExceptions(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

		Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(id, addressId)).withSelfRel();

		Link addressesLink = linkTo(methodOn(UserController.class).getUserAddresses(addressId)).withRel("addresses");

		Link userLink = linkTo(UserController.class).slash(id).withRel("user");

		AddressesRest addressesRest = modelMapper.map(addressDto, AddressesRest.class);

		addressesRest.add(addressLink);
		addressesRest.add(userLink);
		addressesRest.add(addressesLink);

		return addressesRest;
	}

	@GetMapping(path = "/email-verification", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	// @CrossOrigin(origins = "*") // This statement allow us all other domains
	// communicate this url
	// @CrossOrigin(origins = "http://localhost:8083") // This statement allow us
	// only localhost:8083 communicate this url
	// @CrossOrigin(origins = {"http://localhost:8083" , "http://localhost:8084" })
	// // This statement allow us both
	// localhost:8083 and localhost:8084 communicate this url
	public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {

		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());

		boolean isVerified = userService.verifyEmailToken(token);
		if (isVerified)
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		else
			returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

		return returnValue;
	}

	@PostMapping(path = "/password-reset-request", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public OperationStatusModel requestReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
		OperationStatusModel returnValue = new OperationStatusModel();

		boolean operationResult = userService.requestPasswordReset(passwordResetRequestModel.getEmail());

		returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
		returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

		if (operationResult) {
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		}

		return returnValue;

	}

	@PostMapping(path = "/password-reset", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public OperationStatusModel resetPassword(@RequestBody PasswordResetModel passwordResetModel) {
		OperationStatusModel returnValue = new OperationStatusModel();

		boolean operationResult = userService.resetPassword(passwordResetModel.getToken(),
				passwordResetModel.getPassword());

		returnValue.setOperationName(RequestOperationName.PASSWORD_RESET.name());
		returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

		if (operationResult) {
			returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		}

		return returnValue;
	}

	public final class Constants {
		public static final String API_DESCRIPTION = "Bearer JWT Token";
		public static final String API_OPERATION_NOTE = "This Web Service Endpoit returns User Details. "
				+ "User public user id in URL Path. For example : /users/fhojdaq2345";
	}

}
