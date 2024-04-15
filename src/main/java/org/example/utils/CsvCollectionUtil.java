package org.example.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvCollectionUtil {

    public static Object[][] getDataMap(String filepath) {
        CsvMapper mapper = new CsvMapper();
        File csvFile = new File(filepath);

        try (MappingIterator<String[]> it = mapper.readerFor(String[].class)
                .with(CsvParser.Feature.WRAP_AS_ARRAY)
                .readValues(csvFile)) {

            List<Object[]> data = new ArrayList<>();
            if (it.hasNext()) {
                it.next();
            }
            while (it.hasNext()) {
                String[] row = it.next();
                data.add(row);
            }
            return data.toArray(new Object[0][]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
