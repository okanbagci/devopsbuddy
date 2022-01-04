package com.devopsbuddy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.devopsbuddy.web.i18n.I18NService;

@SpringBootTest
class DevopsbuddyApplicationTests {

	@Autowired
	private I18NService i18NService;

	@Test
	public void testMessageByLocaleService() throws Exception {
		String expectedResult = "Bootstrap starter template";
		String messageId = "index.main.callout";
		String actual = i18NService.getMessage(messageId);
		assertEquals(expectedResult, actual, "The actual and expected Strings don't match");
	}

}
