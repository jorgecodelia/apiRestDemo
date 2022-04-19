package com.code.ms.test.controller;

import com.code.ms.test.business.TestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TestControllerTest {

  @InjectMocks private TestController testController;
  @Mock private TestService testService;

  @Test
  void shuldDoTest() {}
}
