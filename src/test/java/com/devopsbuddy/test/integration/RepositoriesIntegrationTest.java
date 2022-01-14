package com.devopsbuddy.test.integration;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.devopsbuddy.backend.persistence.domain.backend.Role;
import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.devopsbuddy.backend.persistence.repositories.UserRepository;
import com.devopsbuddy.enums.PlansEnum;
import com.devopsbuddy.enums.RolesEnum;

@SpringBootTest
public class RepositoriesIntegrationTest {

	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void init() {
		Assert.assertNotNull(planRepository);
		Assert.assertNotNull(roleRepository);
		Assert.assertNotNull(userRepository);
	}
	
	@Test
    public void testCreateNewPlan() throws Exception {
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		Optional<Plan> retrievePlan = planRepository.findById(PlansEnum.BASIC.getId());
		Assert.assertNotNull(retrievePlan);

    }
	
	@Test
	public void testCreateNewRole() throws Exception {
		Role userRole = createRole(RolesEnum.BASIC);
		roleRepository.save(userRole);
		
		Optional<Role> retrieveRole = roleRepository.findById(RolesEnum.BASIC.getId());
		Assert.assertNotNull(retrieveRole);
	}
	
	@Test
	public void testCreateNewUser() throws Exception {
		Plan basicPlan = createPlan(PlansEnum.BASIC);
		planRepository.save(basicPlan);
		
		User basicUser = createBasicUser();
		basicUser.setPlan(basicPlan);
		
		Role basicRole = createRole(RolesEnum.BASIC);
		Set<UserRole> userRoles = new HashSet<>();
		UserRole userRole = new UserRole(basicUser, basicRole);
		userRoles.add(userRole);
		
		basicUser.getUserRoles().addAll(userRoles);
		
		for(UserRole ur: userRoles) {
			roleRepository.save(ur.getRole());
		}
		
		basicUser = userRepository.save(basicUser);
		Optional<User> newlyCreatedUser = userRepository.findById(basicUser.getId());
		Assert.assertNotNull(newlyCreatedUser.get());
		Assert.assertTrue(newlyCreatedUser.get().getId() != 0);
		Assert.assertNotNull(newlyCreatedUser.get().getPlan());
		Assert.assertNotNull(newlyCreatedUser.get().getPlan().getId());
		Set<UserRole> newlyCreatedUserUserRoles = newlyCreatedUser.get().getUserRoles();
		for(UserRole ur : newlyCreatedUserUserRoles) {
			Assert.assertNotNull(ur.getRole());
			Assert.assertNotNull(ur.getRole().getId());
		}
	}
	
	// private methods
	
	private Plan createPlan(PlansEnum plansEnum) {
		return new Plan(plansEnum);
	}
	
	private Role createRole(RolesEnum rolesEnum) {
		return new Role(rolesEnum);
	}
	
	private User createBasicUser() {
		User user = new User();
		user.setUsername("basicUser");
		user.setPassword("secret");
		user.setEmail("o@b.com");
		user.setFirstName("Okan");
		user.setLastName("Bagci");
		user.setPhoneNumber("12345678");
		user.setCountry("TR");
		user.setEnabled(true);
		user.setDescription("A basic user");
		user.setProfileImageUrl("https://blabla.images.com/basicuser");
		
		return user;
	}
}
