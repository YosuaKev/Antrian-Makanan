import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class Order {
    String foodItem;
    int quantity;

    public Order(String foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public String toString() {
        return foodItem + " (Quantity: " + quantity + ")";
    }
}

public class Makanan extends JFrame implements ActionListener {
    // Variabel GUI
    private JTextField foodInput, quantityInput;
    private JTextArea orderList;
    private JButton addOrderButton, processOrderButton;
    private JLabel statusLabel;

    // Variabel untuk menyimpan pesanan
    private Stack<Order> orderStack;
    private Queue<Order> orderQueue;

    public Makanan() {
        setTitle("Restaurant Order System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inisialisasi GUI
        JPanel panel = new JPanel(new GridLayout(4, 2));
        foodInput = new JTextField();
        quantityInput = new JTextField();
        orderList = new JTextArea();
        orderList.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderList);
        addOrderButton = new JButton("Add Order");
        processOrderButton = new JButton("Process Order");
        statusLabel = new JLabel("");

        // Tambahkan komponen GUI ke panel
        panel.add(new JLabel("Food Item:"));
        panel.add(foodInput);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityInput);
        panel.add(addOrderButton);
        panel.add(processOrderButton);
        panel.add(statusLabel);
        panel.add(scrollPane);

        // Inisialisasi stack dan queue
        orderStack = new Stack<>();
        orderQueue = new LinkedList<>();

        // Tambahkan listener ke tombol
        addOrderButton.addActionListener(this);
        processOrderButton.addActionListener(this);

        // Tambahkan panel ke frame
        add(panel);
    }

    // Method untuk menambahkan pesanan ke stack
    private void addOrderToStack(Order order) {
        orderStack.push(order);
        updateOrderList();
    }

    // Method untuk menambahkan pesanan ke queue
    private void addOrderToQueue(Order order) {
        orderQueue.add(order);
        updateOrderList();
    }

    // Method untuk memperbarui tampilan daftar pesanan
    private void updateOrderList() {
        orderList.setText("");
        for (Order order : orderStack) {
            orderList.append(order.toString() + "\n");
        }
    }

    // Method untuk menangani aksi tombol
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addOrderButton) {
            String foodItem = foodInput.getText();
            int quantity = Integer.parseInt(quantityInput.getText());
            Order order = new Order(foodItem, quantity);
            addOrderToStack(order);
        } else if (e.getSource() == processOrderButton) {
            if (!orderStack.isEmpty()) {
                Order order = orderStack.pop();
                statusLabel.setText("Processing Order: " + order.toString());
                // Proses pesanan (simulasi)
                try {
                    Thread.sleep(2000); // Delay 2 detik untuk simulasi
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                statusLabel.setText("Order Processed: " + order.toString());
                addOrderToQueue(order);
            } else {
                statusLabel.setText("No orders to process!");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Makanan().setVisible(true);
        });
    }
}
