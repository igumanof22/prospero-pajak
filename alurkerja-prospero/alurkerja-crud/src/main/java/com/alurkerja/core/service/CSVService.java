package com.alurkerja.core.service;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class CSVService<D> {
    public List<D> fromCSV(InputStream stream,
                           String separator) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(stream, byteArrayOutputStream);
        stream.close();
        List<D> result = new ArrayList<>();
        String content = new String(byteArrayOutputStream.toByteArray());
        String[] lines = content.split("\\r?\\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
//            if (StringUtils.isEmpty(line)) {
//                break;
//            }
            D row = fromCSV(line.split(separator));
            if(row != null) {
                result.add(row);
            }
        }
        return result;
    }
    public D fromCSV(String[] lines){
        return null;
    }

    public List<D> uploadFromCSV(InputStream stream,
                                 String separator) throws IOException {
        List<D> dtos = this.fromCSV(stream, separator);
        for(D e : dtos){
            this.createEntity(e);
        }
        return dtos;
    }

    public abstract D createEntity(D dto);

    public String convertToCSV(D dto) {
        List<String> result = new ArrayList<>();
        for(String column : getCSVColumns()) {
            try {
                result.add(BeanUtils.getProperty(dto, column));
            }
            catch(Exception ex) {
                //fail getting field
                result.add("");
            }
        }
        return String.join(";", result);
    }

    public String[] getCSVColumns() {
        return new String[]{"id", "name"};
    }


    public byte[] toCSV(List<D> dtos) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PrintWriter pw = new PrintWriter(out)) {
            pw.println(getCSVHeader());
            dtos.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        }
        return out.toByteArray();
    }

    public byte[] startExport(List<D> dtos) {
        try {
            return toCSV(dtos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCSVHeader(){
        String[] header = new String[getCSVColumns().length];
        int i = 0;
        for(String s : getCSVColumns()){
            header[i++] = splitCamelCase(s).toUpperCase();
        }
        return String.join(";", header);
    }

    private String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
}
