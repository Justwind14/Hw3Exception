import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UserDataExceptionHandling {
    public void UserData() {
        try {
            System.out.println("Хэллоу. Введите свои данные ЧЕРЕЗ ПРОБЕЛ в следующем порядке: ФИО датаРожения(dd.mm.yyyy)" +
                    " номерТелефона, пол(f(женщина) или m).");
            writeToFileDate(inputStr());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] inputStr() {
        String resultStr;
        String[] checkArray;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            resultStr = reader.readLine();
            checkArray = resultStr.split(" ");
            if (checkLengthDate(checkArray)) {
                throw new RuntimeException("Данная строка является не валидной. Проверь длинну строки");
            }
            if (!checkBirthdayValid(checkArray)) {
                throw new RuntimeException("Неверный формат даты");
            }
            if (!genderChek(checkArray)) {
                throw new RuntimeException("Указан неверный пол");
            }
            if (!checkNumber(checkArray)) {
                throw new RuntimeException("Указан неверный формат номера");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return checkArray;
    }

    private boolean checkLengthDate(String[] checkStr) {
        return checkStr.length != 6;
    }

    private boolean checkBirthdayValid(String[] checkStr) { 
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            simpleDateFormat.format(simpleDateFormat.parse(checkStr[3]));
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean genderChek(String[] checkStr) {
        if (checkStr[checkStr.length - 1].toLowerCase().charAt(0) == 'f'
                || checkStr[checkStr.length - 1].toLowerCase().charAt(0) == 'm'
                && checkStr[checkStr.length - 1].length() == 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkNumber(String[] checkStr) {
        try {
            Long.parseLong(checkStr[checkStr.length - 2]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void writeToFileDate(String[] str) throws IOException {
        BufferedWriter bufferedReader = new BufferedWriter(new FileWriter(str[0], true));
        StringBuilder st = new StringBuilder();
        if (checkAllreadyAdded(str)) {
            for (int i = 0; i < str.length; i++) {
                st.append(str[i]).append(" ");
            }
            bufferedReader.write(st + "\n");
            bufferedReader.flush();
            bufferedReader.close();
        } else {
            System.err.println("Данный абонент уже находится в списке");
            UserData();
        }
    }

    private boolean checkAllreadyAdded(String[] strings) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(strings[0]));
        String line;
        int count;
        while ((line = bufferedReader.readLine()) != null) {
            count = 0;
            String[] checkStr = line.split(" ");
            for (int i = 1; i < strings.length; i++) {
                if (!strings[i].equals(checkStr[i])) {
                    count++;
                    break;
                }
            }
            if (count == 0) {
                return false;
            }
        }
        bufferedReader.close();
        return true;
    }
}
