package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class MainScreenController implements Initializable
{

	@FXML
	private TextArea UserEntryTextArea;

	@FXML
	private TextArea ResultsTextArea;

	@FXML
	private ToggleButton RAWToggleButton;

	@FXML
	private ToggleButton JPEGToggleButton;

	@FXML
	private Button SendPhotosButton;

	@FXML
	private Button ClearResultsButton;

	@FXML
	private TextField FilepathTextField;

	private ToggleGroup menuToggleGroup;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		menuToggleGroup = new ToggleGroup();

		//buttons to choose what file type the pictures are
		RAWToggleButton.setToggleGroup(menuToggleGroup);
		JPEGToggleButton.setToggleGroup(menuToggleGroup);

		//initialize with RAW button selected
		RAWToggleButton.setSelected(true);
	}

	@FXML
	public void OnRAWClicked (ActionEvent event)
	{
		RAWToggleButton.setSelected(true);
		JPEGToggleButton.setSelected(false);
	}

	@FXML
	public void OnJPEGClicked (ActionEvent event) 
	{
		JPEGToggleButton.setSelected(true);
		RAWToggleButton.setSelected(false);
	}

	@FXML
	public void OnSendClicked (ActionEvent event) 
	{
		// reset results text
		ResultsTextArea.setText("");

		ArrayList<String> entries = getEntries();
		
		//take valid entries and convert them into actual file names
		formatEntries(entries);

		String filePath = FilepathTextField.getText();

	}

	@FXML
	public void OnClearResultsClicked (ActionEvent event) 
	{
		ResultsTextArea.setText("");
	}

	/**
	 * Read and validate all entries from the UserEntryTextArea
	 * @return		all valid (integer) entries
	 */
	public ArrayList<String> getEntries()
	{
		String[] entryText = UserEntryTextArea.getText().split("\\W+");

		ArrayList<String> entries = new ArrayList<String>();

		for(int i = 0; i < entryText.length; i++)
		{
			try
			{
				//test if entry is an integer
				int test = Integer.parseInt(entryText[i]);

				//exception wasn't thrown so it is a valid integer value
				entries.add(entryText[i]);
			}
			catch(NumberFormatException e)
			{
				// The tested entry was not an integer
				if(ResultsTextArea.getText().isEmpty())
				{
					// initialize results text
					ResultsTextArea.setText("Error processing entries: \n" + entryText[i]);
				}
				else
				{
					//add error to results text
					ResultsTextArea.setText(ResultsTextArea.getText() +  ", " + entryText[i]);
				}
			}
		}

		//add some space between errors and other results
		ResultsTextArea.setText(ResultsTextArea.getText() + "\n\n");


		return entries;
	}
	
	public void formatEntries(ArrayList<String> entries)
	{
		String format = "";
		
		if(RAWToggleButton.isSelected())
		{
			//images are RAW files
			format = ".CR2";
		}
		else
		{
			//images are JPEG files
			format = ".jpg";
		}
		
		for(int i = 0; i < entries.size(); i++)
		{
			entries.set(i, "IMG_" + entries.get(i) + format);
		}
	}
}
