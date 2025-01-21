import javax.swing.*;
import java.awt.*;

public class Calculator {

    private String firstNum = "";
    private String operator = "";
    private boolean resultDisplayed= false;

    public Calculator(){

        int fieldWidth = 400;
        int fieldHeight = 500;

        //Frame Part
        JFrame frame = new JFrame("Calculator");
        frame.setSize(fieldWidth, fieldHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); 

        //Result Field
        JTextField resultField = new JTextField();
        resultField.setEditable(false);
        resultField.setFont(new Font("Arial", Font.PLAIN, 35));
        resultField.setHorizontalAlignment(JTextField.RIGHT);
        frame.add(resultField, BorderLayout.NORTH);

        //About part
        JTextField about = new JTextField();
        about.setEditable(false);
        about.setFont(new Font("Arial", Font.BOLD, 12));
        about.setHorizontalAlignment(JTextField.CENTER);
        frame.add(about, BorderLayout.SOUTH);
        about.setText("Simple Calculator v1.0   © tzhevan");

        // Button Field
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(2,2,2,2);

        //Backspace Button
        JButton backspaceButton = new JButton("Backspace");
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        backspaceButton.setFocusPainted(false);
        backspaceButton.addActionListener(e-> {
            String currentText=resultField.getText();
            if (currentText.length() > 0) {
                resultField.setText(currentText.substring(0, currentText.length() - 1));
            }
        });
        panel.add(backspaceButton, gbc);

        // Clear Button
        JButton clearButton = new JButton("AC");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        clearButton.setFocusPainted(false);
        panel.add(clearButton, gbc);
        clearButton.addActionListener(e -> {
            resultField.setText("");
            firstNum = "";
            operator = "";
            resultDisplayed = false;
        });


        // Other Buttons
        String[] buttons={
            "7", "8", "9", "÷",
            "4", "5", "6", "×",
            "1", "2", "3", "-",
            ".", "0", "=", "+"
        };
        
        int row=1;
        Font buttonFont = new Font("Arial", Font.PLAIN, 24);
        for(int i=0; i<buttons.length; i++){
            JButton button = new JButton(buttons[i]);
            button.setFont(buttonFont);
            gbc.gridx = i % 4;
            gbc.gridy = i / 4 + 1;
            gbc.gridwidth = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            button.setFocusPainted(false);
            button.addActionListener(e -> buttonCalculation(button.getText(), resultField));
            panel.add(button, gbc);
            if (i % 4 == 3) {
                row++;
            }
        }

        
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }


    private void buttonCalculation(String textOnButton, JTextField textField){
        if("0123456789".contains(textOnButton)){
            if(resultDisplayed){
                textField.setText("");
                resultDisplayed=false;
            }
            textField.setText(textField.getText()+textOnButton);
    
        } else if (".".equals(textOnButton)) {
            if (!textField.getText().contains(".")) {
                textField.setText(textField.getText() + ".");
            }
        } else if("=".equals(textOnButton)){
            String secondNum = textField.getText();
            double result = 0;
    
            switch(operator){
                case "+":
                    result = Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
                    break;
                case "-":
                    result = Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
                    break;
                case "×":
                    result = Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
                    break;
                case "÷":
                    if(Double.parseDouble(secondNum) == 0){
                        textField.setText("Math Error: Divide by 0");
                        firstNum = "";
                        operator = "";
                        resultDisplayed = false;
                        return;
                    }
                    result = Double.parseDouble(firstNum) / Double.parseDouble(secondNum);
                    break;
            }

            if (result == (int) result) {
                textField.setText(String.valueOf((int) result));
            } else {
                textField.setText(String.valueOf(result));
            }
    
            firstNum = String.valueOf(result);
            operator = "";
            resultDisplayed = true;
        } else {
            firstNum = textField.getText();
            operator = textOnButton;
            textField.setText("");
            resultDisplayed = false;
        }
    }
    
    public static void main(String[] args) {
        new Calculator();
    }
}
