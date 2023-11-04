package com.example.countryguesser.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class GetAdminStats {
    private List<AdminStatistics> adminStatistics;
}
