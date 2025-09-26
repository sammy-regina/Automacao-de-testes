package br.com.sandra.automation.support;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    private static final String EXCEL_FILE_PATH = "target/relatorio_testes.xlsx";

    public static void salvarResultado(String tipoTeste, String idCenario, String valorAtual, String valorEsperado, String resultado) {
        Workbook workbook = null;
        Sheet sheet = null;
        File file = new File(EXCEL_FILE_PATH);

        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
                fis.close();
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Resultados dos Testes");
                // Cria o cabeçalho se o arquivo é novo
                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Tipo de Teste");
                headerRow.createCell(1).setCellValue("ID do Cenário");
                headerRow.createCell(2).setCellValue("Valor Atual");
                headerRow.createCell(3).setCellValue("Valor Esperado");
                headerRow.createCell(4).setCellValue("Resultado");
            }

            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);

            newRow.createCell(0).setCellValue(tipoTeste);
            newRow.createCell(1).setCellValue(idCenario);
            newRow.createCell(2).setCellValue(valorAtual);
            newRow.createCell(3).setCellValue(valorEsperado);
            newRow.createCell(4).setCellValue(resultado);

            FileOutputStream fos = new FileOutputStream(EXCEL_FILE_PATH);
            workbook.write(fos);
            fos.close();
            System.out.println("✔️ Resultado salvo no Excel: " + EXCEL_FILE_PATH);

        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar resultado no Excel: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
