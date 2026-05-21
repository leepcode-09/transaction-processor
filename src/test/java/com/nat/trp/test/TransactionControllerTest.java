package com.nat.trp.test;

import com.nat.trp.controller.TransactionController;
import com.nat.trp.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void getAccounts_whenNoAccounts_returnsEmptyArray() throws Exception {
        when(accountService.getAllAccounts()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/tps/app/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAccounts_whenAccountsExist_returnsList() throws Exception {
        com.nat.trp.entity.Account a1 = mock(com.nat.trp.entity.Account.class);
        when(a1.getAcctId()).thenReturn(1L);

        com.nat.trp.entity.Account a2 = mock(com.nat.trp.entity.Account.class);
        when(a2.getAcctId()).thenReturn(2L);

        when(accountService.getAllAccounts()).thenReturn(Arrays.asList(a1, a2));

        mockMvc.perform(get("/api/v1/tps/app/accounts")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].acctId", is(1)))
                .andExpect(jsonPath("$[1].acctId", is(2)));
    }

}

