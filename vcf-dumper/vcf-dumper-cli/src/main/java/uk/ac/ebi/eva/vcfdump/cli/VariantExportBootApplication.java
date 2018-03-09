/*
 * Copyright 2015-2016 EMBL - European Bioinformatics Institute
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
package uk.ac.ebi.eva.vcfdump.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import uk.ac.ebi.eva.vcfdump.lib.VariantExporterController;

/**
 * The variant exporter tool allows to dump a valid VCF from a query against
 * the EVA database.
 * <p>
 * Mandatory arguments are: species, database name, studies and files
 * Optional arguments are: output directory
 */
@SpringBootApplication
public class VariantExportBootApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(VariantExportBootApplication.class);


    @Override
    public void run(String[] args) throws Exception {
        VariantExporterController variantExporterController = new VariantExporterController("example_db");
        variantExporterController.run();
    }

    public static void main(String[] args) {
        SpringApplication.run(VariantExportBootApplication.class, args);
    }

}
