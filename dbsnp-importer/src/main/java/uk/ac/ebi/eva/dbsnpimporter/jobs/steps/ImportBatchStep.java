/*
 * Copyright 2017 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ebi.eva.dbsnpimporter.jobs.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import uk.ac.ebi.eva.commons.core.models.IVariant;
import uk.ac.ebi.eva.dbsnpimporter.configurations.ImportBatchStepConfiguration;
import uk.ac.ebi.eva.dbsnpimporter.models.SubSnpCoreFields;

import static uk.ac.ebi.eva.dbsnpimporter.configurations.ImportBatchStepConfiguration.VARIANTS_PROCESSOR;
import static uk.ac.ebi.eva.dbsnpimporter.configurations.ImportBatchStepConfiguration.VARIANTS_READER;
import static uk.ac.ebi.eva.dbsnpimporter.configurations.ImportBatchStepConfiguration.VARIANTS_WRITER;

@Configuration
@EnableBatchProcessing
@Import({ImportBatchStepConfiguration.class})
public class ImportBatchStep {

    private static final Logger logger = LoggerFactory.getLogger(ImportBatchStep.class);

    private static final String LOAD_VARIANTS_STEP = "LOAD_VARIANTS_STEP";

    @Autowired
    @Qualifier(VARIANTS_READER)
    private ItemStreamReader<SubSnpCoreFields> reader;

    @Autowired
    @Qualifier(VARIANTS_PROCESSOR)
    private ItemProcessor<SubSnpCoreFields, IVariant> processor;

    @Autowired
    @Qualifier(VARIANTS_WRITER)
    private ItemWriter<IVariant> writer;

    @Bean(LOAD_VARIANTS_STEP)
    public Step loadVariantsStep(StepBuilderFactory stepBuilderFactory,
                                 SimpleCompletionPolicy chunkSizeCompletionPolicy) {
        logger.debug("Building '" + LOAD_VARIANTS_STEP + "'");

        return stepBuilderFactory.get(LOAD_VARIANTS_STEP)
                .<SubSnpCoreFields, IVariant>chunk(chunkSizeCompletionPolicy)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}