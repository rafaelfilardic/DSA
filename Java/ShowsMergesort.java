import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class Show {
    private String show_id;
    private String type;
    private String title;
    private String rightector;
    private String[] cast;
    private String country;
    private Date date_added;
    private int release_year;
    private String rating;
    private String duration;
    private String[] listed_in;

    public Show() {} //Default constructor for the class

    public String getShowId() { return show_id; }
    public String getType() { return type; }
    public String getTitle() { return title; }
    public String getrightector() { return rightector; }
    public String[] getCast() { return cast; }
    public String getCountry() { return country; }
    public Date getDateAdded() { return date_added; }
    public int getReleaseYear() { return release_year; }
    public String getRating() { return rating; }
    public String getDuration() { return duration; }
    public String[] getListedIn() { return listed_in; }

    public void setShowId(String show_id) { this.show_id = show_id; }
    public void setType(String type) { this.type = type; }
    public void setTitle(String title) { this.title = title; }
    public void setrightector(String rightector) { this.rightector = rightector; }
    public void setCast(String[] cast) { this.cast = cast; }
    public void setCountry(String country) { this.country = country; }
    public void setDateAdded(Date date_added) { this.date_added = date_added; }
    public void setReleaseYear(int release_year) { this.release_year = release_year; }
    public void setRating(String rating) { this.rating = rating; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setListedIn(String[] listed_in) { this.listed_in = listed_in; }

    @Override
    public String toString() {
        return "Show{" +
               "\n  show_id='" + show_id + '\'' +
               ",\n  type='" + type + '\'' +
               ",\n  title='" + title + '\'' +
               ",\n  rightector='" + rightector + '\'' +
               ",\n  cast=" + Arrays.toString(cast) +
               ",\n  country='" + country + '\'' +
               ",\n  date_added=" + date_added +
               ",\n  release_year=" + release_year +
               ",\n  rating='" + rating + '\'' +
               ",\n  duration='" + duration + '\'' +
               ",\n  listed_in=" + Arrays.toString(listed_in) +
               "\n}";
    }

    /**
     * This method reads a line from the 'disneyplus.csv' file and formats it into a Show object.
     * It handles fields that contain commas inside double quotes.
     *
     * @param csvLine The CSV line to be read.
     * @return A Show object with the formmated data.
     */
    public Show readCSV(String csvLine) {
        List<String> fields = new ArrayList<>(); //List to hold the extracted fields from the CSV line
        StringBuilder currentField = new StringBuilder();
        boolean quotes = false;

        //Iterates through the line to split fields while respecting double quotes
        for(int i = 0; i < csvLine.length(); i++) {
            char charactere = csvLine.charAt(i);

            if(charactere == '\"') {
                quotes = !quotes;
            }
            //Adds the completed field to the list and resets the StringBuilder
            else if(charactere == ',' && !quotes) {
                fields.add(currentField.toString().trim());
                currentField = new StringBuilder();
            }
            else {
                currentField.append(charactere);
            }
        }

        fields.add(currentField.toString().trim()); //Adds the last field after the loop finishes

        Show show = new Show(); //Creates a new Show object and populates its fields
        String value;

        show.setShowId(fields.get(0).isEmpty() ? "NaN" : fields.get(0));

        show.setType(fields.get(1).isEmpty() ? "NaN" : fields.get(1));

        show.setTitle(fields.get(2).isEmpty() ? "NaN" : fields.get(2));

        value = fields.get(3).isEmpty() ? "NaN" : fields.get(3);
        show.setrightector(value);

        value = fields.get(4).isEmpty() ? "NaN" : fields.get(4);
        if(value.equals("NaN")) {
            show.setCast(new String[]{value});
        }
        else {
            String[] castArray = value.split(",\\s*");
            Arrays.sort(castArray); //Sorts the cast array alphabetically (needed for the execise)
            show.setCast(castArray);
        }

        value = fields.get(5).isEmpty() ? "NaN" : fields.get(5);
        show.setCountry(value);

        SimpleDateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        String dateString = fields.get(6).trim();
        try {
            if(dateString.isEmpty()) {
                //Assigns 'March 1, 1900' to an empty date
                show.setDateAdded(format.parse("March 1, 1900"));
            }
            else {
                //Tries parsing the date
                show.setDateAdded(format.parse(dateString));
            }
        }
        catch(ParseException e) {
            System.err.println("Error converting date: " + dateString);
            show.setDateAdded(null);
        }

        try {
            show.setReleaseYear(Integer.parseInt(fields.get(7)));
        }
        catch(NumberFormatException e) {
            System.err.println("Error converting release year: " + fields.get(7));
            show.setReleaseYear(-1); //Default value in case of error
        }

        show.setRating(fields.get(8).isEmpty() ? "NaN" : fields.get(8));

        show.setDuration(fields.get(9).isEmpty() ? "NaN" : fields.get(9));

        value = fields.get(10).isEmpty() ? "NaN" : fields.get(10);
        if (value.equals("NaN")) {
            show.setListedIn(new String[]{value});
        }
        else {
            String[] listedInArray = (value.split(",\\s*"));
            Arrays.sort(listedInArray); //Sorts the listed_in array alphabetically (needed for the execise)
            show.setListedIn(listedInArray);
        }
        
        return show;
    }

    public void print(Show show) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        String formattedDate = "";
        if(show.getDateAdded() != null) {
            formattedDate = formatter.format(show.getDateAdded());
        }
        else {
            formattedDate = "NaN"; //In case date is null
        }

        //Formats the arrays into a string
        String formattedCast = (show.getCast() != null) ? Arrays.toString(show.getCast()) : "[NaN]";
        String formattedListedIn = (show.getListedIn() != null) ? Arrays.toString(show.getListedIn()) : "[NaN]";

        System.out.printf("=> %s ## %s ## %s ## %s ## %s ## %s ## %s ## %s ## %s ## %s ## %s ##\n",
                show.getShowId(),
                show.getTitle(),
                show.getType(),
                show.getrightector(),
                formattedCast,
                show.getCountry(),
                formattedDate,
                show.getReleaseYear(),
                show.getRating(),
                show.getDuration(),
                formattedListedIn);
    }
}

public class Shows {
    //Method to merge two sorted sub-lists
    public static void merge(ArrayList<Show> list, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        //Creates temporary sub-lists
        ArrayList<Show> leftArray = new ArrayList<>();
        ArrayList<Show> rightArray = new ArrayList<>();

        //Copies data to the temporary sub-lists
        for(int i = 0; i < n1; ++i) {
            leftArray.add(list.get(left + i));
        }
        for(int j = 0; j < n2; ++j) {
            rightArray.add(list.get(middle + 1 + j));
        }

        int i = 0, j = 0;
        int k = left;
        
        //Merges the temporary sub-lists back into the main list
        while(i < n1 && j < n2) {
            if(leftArray.get(i).getTitle().compareTo(rightArray.get(j).getTitle()) <= 0) {
                list.set(k, leftArray.get(i));
                i++;
            }
            else {
                list.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }

        //Copies any remaining elements from the left sub-list
        while(i < n1) {
            list.set(k, leftArray.get(i));
            i++;
            k++;
        }

        //Copies any remaining elements from the right sub-list
        while(j < n2) {
            list.set(k, rightArray.get(j));
            j++;
            k++;
        }
    }

    //Main Mergesort method to sort the list by title
    public static void mergesort(ArrayList<Show> list, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergesort(list, left, middle);
            mergesort(list, middle + 1, right);
            merge(list, left, middle, right);
        }
    }


    public static void main(String[] args) {
        ArrayList<Show> shows = new ArrayList<>(); //Creates a list to save show objects
        String filePath = "disneyplus.csv";

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); //Skips the CSV header line

            String line;
            Show showReader = new Show(); 

            while ((line = br.readLine()) != null) {
                shows.add(showReader.readCSV(line)); //Reads each line and add into the ArrayList 
            }
        }   
        catch(IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return; //Exits the program if file cannot be read
        }

        Scanner sc = new Scanner(System.in);
        ArrayList<Show> userShows = new ArrayList<>();

        String input;
        while(!(input = sc.nextLine()).equals("FIM")) {
            try {
                //Removes the 's' prefix and converts the input to an int
                String showIdString = input.substring(1); 
                int showId = Integer.parseInt(showIdString);

                //Cheks if the show ID is valid
                if(showId > 0 && showId <= shows.size()) {
                    userShows.add(shows.get(showId - 1));
                }
                else {
                    System.out.println("Invalid show ID: " + showId);
                }
            }
            catch(NumberFormatException e) {
                System.err.println("Invalid input.");
            }
        }

        mergesort(userShows, 0, userShows.size() - 1);

        for(Show show : userShows) {
            show.print(show);
        }

        sc.close();
    }
}
