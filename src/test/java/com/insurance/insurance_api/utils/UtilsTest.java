package com.insurance.insurance_api.utils;

import com.insurance.insurance_api.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UtilsTest {

    private static CompanyRepository companyRepository;

    @BeforeEach
    public void setUp() {
        companyRepository = Mockito.mock(CompanyRepository.class);
    }


    @Test
    void shouldGenerateIdentifierWithMissingSuffix(){
        String result = Utils.generateCompanyIdentifier("TE", companyRepository);
        assertEquals("TEX-1", result);
    }

    @Test
    void shouldGenerateIdentifierWithImcrementingSuffix(){
        when(companyRepository.existsByCompanyIdentifier("VAU-1")).thenReturn(true);
        when(companyRepository.existsByCompanyIdentifier("VAU-2")).thenReturn(false);

        String result = Utils.generateCompanyIdentifier("Vaudoise Assurance", companyRepository);
        assertEquals("VAU-2", result);
    }

    @Test
    void shouldGenerateIdentifierWithoutIncrementingSuffix(){
        when(companyRepository.existsByCompanyIdentifier("VAU-1")).thenReturn(false);

        String result = Utils.generateCompanyIdentifier("Vaudoise Assurance", companyRepository);
        assertEquals("VAU-1", result);
    }

    @Test
    void shouldThrowExceptionForEmptyName(){
        assertThrows(IllegalArgumentException.class, ()  ->
                Utils.generateCompanyIdentifier("",companyRepository)
        );
    }

    @Test
    void shouldThrowExceptionForNullName() {
        assertThrows(IllegalArgumentException.class, () ->
                Utils.generateCompanyIdentifier(null, companyRepository)
        );
    }
}
