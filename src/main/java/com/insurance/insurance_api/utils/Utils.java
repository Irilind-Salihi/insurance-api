package com.insurance.insurance_api.utils;

import com.insurance.insurance_api.repository.CompanyRepository;

public class Utils {

    public static String generateCompanyIdentifier(String companyName, CompanyRepository companyRepository) {
        String identifier;

        if (companyName == null || companyName.isEmpty()) {
            throw new IllegalArgumentException("company name is null or empty");
        }

        String prefix = companyName.trim().replaceAll("[^a-zA-Z0-9]", "").toUpperCase();

        if (prefix.length()< 3 ) {
            prefix = String.format("%-3s", prefix).replace(' ', 'X');
        } else if (prefix.length() > 3) {
            prefix = prefix.substring(0, 3);
        }

        int suffix = 1;
        do{
            identifier = prefix + "-" + suffix++;
        } while (companyRepository.existsByCompanyIdentifier(identifier));

        return identifier;
    }
}
