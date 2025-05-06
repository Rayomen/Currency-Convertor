import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter extends JFrame {
    private JTextField amountField;
    private JComboBox<String> fromCurrency;
    private JComboBox<String> toCurrency;
    private JLabel resultLabel;
    
    private static final Map<String, Map<String, Double>> exchangeRates = new HashMap<>();
    private static final String[] currencies = {"USD", "EUR", "GBP", "JPY"};
    
    static {
        // Initialize exchange rates
        Map<String, Double> usdRates = new HashMap<>();
        usdRates.put("EUR", 0.85);
        usdRates.put("GBP", 0.75);
        usdRates.put("JPY", 110.0);
        exchangeRates.put("USD", usdRates);
        
        Map<String, Double> eurRates = new HashMap<>();
        eurRates.put("USD", 1.18);
        eurRates.put("GBP", 0.88);
        eurRates.put("JPY", 129.53);
        exchangeRates.put("EUR", eurRates);
        
        Map<String, Double> gbpRates = new HashMap<>();
        gbpRates.put("USD", 1.33);
        gbpRates.put("EUR", 1.14);
        gbpRates.put("JPY", 147.25);
        exchangeRates.put("GBP", gbpRates);
        
        Map<String, Double> jpyRates = new HashMap<>();
        jpyRates.put("USD", 0.0091);
        jpyRates.put("EUR", 0.0077);
        jpyRates.put("GBP", 0.0068);
        exchangeRates.put("JPY", jpyRates);
    }
    
    public CurrencyConverter() {
        setTitle("Currency Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));
        
        // Create components
        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);
        
        add(new JLabel("From Currency:"));
        fromCurrency = new JComboBox<>(currencies);
        add(fromCurrency);
        
        add(new JLabel("To Currency:"));
        toCurrency = new JComboBox<>(currencies);
        add(toCurrency);
        
        JButton convertButton = new JButton("Convert");
        add(convertButton);
        
        JButton swapButton = new JButton("Swap Currencies");
        add(swapButton);
        
        add(new JLabel("Result:"));
        resultLabel = new JLabel("0.00");
        add(resultLabel);
        
        // Add action listeners
        convertButton.addActionListener(e -> convertCurrency());
        swapButton.addActionListener(e -> swapCurrencies());
        
        // Set window properties
        setSize(400, 250);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void convertCurrency() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String from = (String) fromCurrency.getSelectedItem();
            String to = (String) toCurrency.getSelectedItem();
            
            if (from.equals(to)) {
                resultLabel.setText(String.format("%.2f %s", amount, to));
                return;
            }
            
            double rate = exchangeRates.get(from).get(to);
            double result = amount * rate;
            
            resultLabel.setText(String.format("%.2f %s", result, to));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void swapCurrencies() {
        int fromIndex = fromCurrency.getSelectedIndex();
        int toIndex = toCurrency.getSelectedIndex();
        
        fromCurrency.setSelectedIndex(toIndex);
        toCurrency.setSelectedIndex(fromIndex);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CurrencyConverter().setVisible(true);
        });
    }
}
