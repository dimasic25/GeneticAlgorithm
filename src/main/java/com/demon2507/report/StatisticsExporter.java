package com.demon2507.report;

import com.demon2507.common.StabilizedGeneticAlgorithmStatistics;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.demon2507.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static com.demon2507.Configuration.getConfig;

public class StatisticsExporter {
    private static final Logger log = LoggerFactory.getLogger(StatisticsExporter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Configuration config;
    private final String algoType;
    private final String REPORT_PATH = "reports";

    public StatisticsExporter(Configuration config, String algoType) {
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.config = config;
        this.algoType = algoType;
    }

    public void export(int averageGenerations, double errorRate) {
        try {
            String configString = objectMapper.writeValueAsString(getConfig());
            String report = """
                    Конфигурация: %s
                    ____________
                    Среднее количество поколений: %s
                    Средняя величина ошибки: %s
                    """.formatted(configString, averageGenerations, errorRate);
            Path reportDirPath = Path.of(REPORT_PATH);
            if (!Files.exists(reportDirPath)) {
                Files.createDirectories(reportDirPath);
            }
            Files.write(Path.of(getPath(errorRate)), report.getBytes(), StandardOpenOption.CREATE);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportStabilizedResults(List<Configuration> configurations,
                                        List<StabilizedGeneticAlgorithmStatistics> realStatistics,
                                        List<StabilizedGeneticAlgorithmStatistics> positionCodeStatistics) {
        exportStabilized("real-statistics.csv", configurations, realStatistics);
        exportStabilized("position-statistics.csv", configurations, positionCodeStatistics);
    }

    private void exportStabilized(String filename,
                                  List<Configuration> configurations,
                                  List<StabilizedGeneticAlgorithmStatistics> stabilizedStatistics) {
        try (var writer = new FileWriter(filename)) {
            // Write header
            writer.append("Метод отбора в новую популяцию,Метод отбора родителей,Вероятность мутации,Величина популяции," +
                    "Среднее кол-во поколений, Средняя величина ошибки, Количество ошибочных запусков\n");
            // Write body
            for (int i = 0; i < configurations.size(); i++) {
                Configuration config = configurations.get(i);
                StabilizedGeneticAlgorithmStatistics stats = stabilizedStatistics.get(i);

                writer.append(config.getNaturalSelectionStrategy().toString()).append(",");
                writer.append(config.getParentsSelectionStrategy().toString()).append(",");
                writer.append(Double.toString(config.getMutationRate())).append(",");
                writer.append(Double.toString(config.getIndividualsInPopulationCount())).append(",");

                writer.append(String.valueOf(stats.averageGenerations())).append(",");
                writer.append(String.valueOf(stats.averageErrorRate())).append(",");
                writer.append(String.valueOf(stats.errorRuns())).append(",");

                writer.append("\n");
            }

            log.info("CSV file has been created successfully!");

        } catch (IOException e) {
            log.error("error while export statistics", e);
        }
    }

    public String getPath(double errorRate) {
        return "%s%s%s-statistics#%s-error%.6f.txt".formatted(REPORT_PATH, File.separator, algoType, config.hashCode(), errorRate);
    }
}
