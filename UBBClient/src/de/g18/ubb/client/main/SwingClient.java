package de.g18.ubb.client.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.g18.ubb.client.communication.JNDIServiceProvider;
import de.g18.ubb.common.domain.BudgetBook;
import de.g18.ubb.common.service.BudgetBookService;
import de.g18.ubb.common.service.exception.NotFoundExcpetion;
import de.g18.ubb.common.service.exception.UserWithEMailNotFound;
import de.g18.ubb.common.service.repository.ServiceRepository;


/**
 * Swing Client, zum testen der Server-API.
 *
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class SwingClient extends JFrame {

    private static final long serialVersionUID = 1L;

    static {
        ServiceRepository.setProvider(new JNDIServiceProvider());
    }

    public static void main(String[] args) {
        SwingClient client = new SwingClient();
        client.setVisible(true);
    }


    private JButton button;
    private JTextArea debugArea;


    public SwingClient() {
        super("UBB - Swing Client");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        debugArea = new JTextArea(30, 50);
        button = new JButton("Do something!");
        button.addActionListener(new ButtonListener());

        add(new JScrollPane(debugArea), BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);

        pack();
    }

    private void doSomething() throws UserWithEMailNotFound, NotFoundExcpetion {
        BudgetBookService service = ServiceRepository.getBudgetBookService();

        log("Creating new BudgetBook...");
        service.createNew("BudgetBook #" + Math.random(), null);

        log("Listing all saved BudgetBooks...");
        List<BudgetBook> books = service.getAllForCurrentUser();
        for (BudgetBook book : books) {
            log("-> {0}", book);
        }
    }

    private void logError(Exception aException) {
        logError("### Error: {0}", aException, aException.getLocalizedMessage());
        System.err.format("### Error: {0}", aException.getLocalizedMessage());
        aException.printStackTrace(System.err);
    }

    private void logError(String aMessage, Exception aException, Object... aMessageParams) {
        log(aMessage, aMessageParams);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        aException.printStackTrace(new PrintStream(outputStream));
        log(outputStream.toString());
    }

    private void log(String aMessage, Object... aMessageParams) {
        String formattedMessage = MessageFormat.format(aMessage, aMessageParams);
        debugArea.append(formattedMessage + "\n");
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private final class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent aEvent) {
            try {
                doSomething();
            } catch (Exception e) {
                logError(e);
            }
        }
    }
}
