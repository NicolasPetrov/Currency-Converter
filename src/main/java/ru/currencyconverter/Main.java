package ru.currencyconverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JComboBox<String> fromCurrency;
    private JComboBox<String> toCurrency;
    private JTextField amountField;
    private JLabel resultLabel;

    public Main() {
        setTitle("Currency Converter");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        String[] currencies = {"USD", "EUR", "CHF", "GBP", "JPY", "CNY", "RUB"};

        fromCurrency = new JComboBox<>(currencies);
        toCurrency = new JComboBox<>(currencies);
        amountField = new JTextField();
        resultLabel = new JLabel("Result: ");

        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(new ConvertAction());

        add(new JLabel("From:"));
        add(fromCurrency);
        add(new JLabel("To:"));
        add(toCurrency);
        add(new JLabel("Amount:"));
        add(amountField);
        add(convertButton);
        add(resultLabel);
    }

    private class ConvertAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String from = (String) fromCurrency.getSelectedItem();
            String to = (String) toCurrency.getSelectedItem();
            double amount = Double.parseDouble(amountField.getText());
            double rate = ExchangeRateFetcher.getExchangeRate(from, to);
            double result = amount * rate;
            resultLabel.setText("Result: " + result);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main converter = new Main();
            converter.setVisible(true);
        });
    }
}