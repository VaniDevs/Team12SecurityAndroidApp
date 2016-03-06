package android.dwabit;

import android.content.Context;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.Random;

/**
 * Created by Andy on 3/5/2016.
 */
public class PopulateBeforeSave {
    String[] randomString = new String[]{"Route 64", "Grove Avenue", "Pin Oak Drive", "Wood Street", "Bayberry Drive", "Route 32", "4th Avenue", "Willow Street", "Wall Street", "Pheasant Run", "Roberts Road", "Grove Street", "Overlook Circle", "Oak Lane", "B Street", "Mulberry Lane", "King Street", "Route 202", "Chapel Street", "Oak Street", "Taylor Street", "Homestead Drive", "Willow Avenue", "8th Street West", "2nd Street", "Valley Drive", "Devonshire Drive", "Fairway Drive", "Aspen Court", "Liberty Street", "Shady Lane", "Surrey Lane", "4th Street West", "Mill Street", "State Street", "Aspen Drive", "Market Street", "Church Street South", "Euclid Avenue", "Holly Drive", "Main Street", "West Street", "5th Street", "Hillcrest Drive", "Catherine Street", "7th Avenue", "5th Street North", "Cemetery Road", "Cambridge Drive", "Front Street North", "Heather Court", "Brookside Drive", "Jones Street", "6th Avenue", "Woodland Avenue", "South Street", "Willow Lane", "Route 41", "Devon Road", "Route 10", "Atlantic Avenue", "Marshall Street", "Adams Street", "Laurel Drive", "Lake Street", "Route 27", "Brook Lane", "York Street", "School Street", "Winding Way", "Warren Street", "Cherry Lane", "Victoria Court", "Franklin Avenue", "Pine Street", "4th Street South", "Clay Street", "Lantern Lane", "Fulton Street", "Windsor Court", "Cambridge Road", "Sycamore Lane", "Route 1", "Washington Avenue", "3rd Street North", "Spruce Avenue", "Hamilton Street", "Schoolhouse Lane", "Jefferson Avenue", "Magnolia Drive", "Sunset Drive", "Sherwood Drive", "5th Street East", "Elizabeth Street", "Hanover Court", "Roosevelt Avenue", "Forest Avenue", "3rd Street West", "Cedar Lane", "Canterbury Drive", "Franklin Court", "Colonial Drive", "6th Street West", "Morris Street", "Highland Drive", "Hill Street", "Hillside Avenue", "Bank Street", "4th Street North", "11th Street", "Linden Street", "Devon Court", "Lilac Lane", "Howard Street", "Madison Avenue", "Evergreen Drive", "Front Street", "Madison Street", "Chestnut Avenue", "Briarwood Drive", "Ridge Road", "Summer Street", "Fairview Road", "Creekside Drive", "Route 44", "Route 20", "Cambridge Court", "Route 9", "Lafayette Avenue", "Hillcrest Avenue", "Maple Lane", "Brown Street", "Broad Street", "Church Street", "Country Club Road", "Ann Street", "Ivy Lane", "Grand Avenue", "Route 6", "Orange Street", "Cleveland Street", "Ridge Street", "Front Street South", "East Avenue", "Hartford Road", "Colonial Avenue", "Lexington Court", "Lincoln Avenue", "12th Street East", "Briarwood Court", "Bridle Court", "Route 17", "Canterbury Road", "Spring Street", "River Street", "2nd Street West", "Meadow Street", "14th Street", "Evergreen Lane", "Lake Avenue", "9th Street West", "Washington Street", "Penn Street", "Oak Avenue", "Willow Drive", "Hudson Street", "Cedar Court", "Edgewood Drive", "4th Street", "Eagle Street", "Depot Street", "Cedar Avenue", "Academy Street", "3rd Avenue", "Bay Street", "Madison Court", "Main Street North", "Sycamore Street", "Green Street", "Somerset Drive", "3rd Street", "Parker Street", "Hickory Lane", "Andover Court", "College Street", "6th Street North", "College Avenue", "1st Street", "Jackson Avenue", "Linden Avenue", "5th Avenue", "Water Street", "Route 2", "Maiden Lane", "Deerfield Drive", "Jackson Street", "Court Street", "Summit Avenue", "White Street", "Magnolia Avenue", "Grant Street", "Hilltop Road", "Locust Street", "Route 100", "Central Avenue", "Pennsylvania Avenue", "Arlington Avenue", "Woodland Road", "2nd Avenue", "Pleasant Street", "Cooper Street", "Lakeview Drive", "Street Road", "10th Street", "Route 29", "Durham Court", "Elm Avenue", "Elmwood Avenue", "Queen Street", "Bridle Lane", "Oxford Road", "Fairview Avenue", "Westminster Drive", "Belmont Avenue", "Forest Street", "Circle Drive", "Ivy Court", "Lafayette Street", "Hamilton Road", "Ashley Court", "Country Club Drive", "Glenwood Avenue", "Lexington Drive", "6th Street", "Sheffield Drive", "Route 7", "Canal Street", "New Street", "Poplar Street", "Mulberry Court", "Crescent Street", "Lawrence Street", "Ridge Avenue", "Cherry Street", "Holly Court", "Route 11", "Broadway", "Charles Street", "Riverside Drive", "Division Street", "Church Road", "13th Street", "Delaware Avenue", "Beechwood Drive", "Hawthorne Lane", "East Street", "12th Street", "Valley Road", "Garfield Avenue", "Inverness Drive", "Maple Avenue", "Virginia Avenue", "James Street", "River Road", "Harrison Avenue", "Sunset Avenue", "Carriage Drive", "Mulberry Street", "Railroad Avenue", "Olive Street", "Oxford Court", "William Street", "Arch Street", "Jefferson Court", "Columbia Street", "Mill Road", "Augusta Drive", "Main Street South", "Park Drive", "Essex Court", "1st Avenue", "Brandywine Drive", "Redwood Drive", "Lincoln Street", "Maple Street", "Cross Street", "John Street", "Route 5", "Buckingham Drive", "Smith Street", "Cypress Court", "Monroe Street", "Orchard Street", "7th Street", "Rosewood Drive", "Harrison Street", "Monroe Drive", "State Street East", "Cleveland Avenue", "5th Street South", "Heather Lane", "Main Street East", "Bridge Street", "Park Street", "Williams Street", "Church Street North", "Meadow Lane", "Prospect Street", "Strawberry Lane", "Myrtle Avenue", "Elm Street", "Cedar Street", "Valley View Road", "Country Lane", "Canterbury Court", "Orchard Avenue", "Primrose Lane", "Dogwood Lane", "Grant Avenue", "9th Street", "Orchard Lane", "Mechanic Street", "Pearl Street", "Walnut Avenue", "Race Street", "Linda Lane", "Glenwood Drive", "Highland Avenue", "Henry Street", "Windsor Drive", "Fawn Lane", "Woodland Drive", "Franklin Street", "North Avenue", "Park Avenue", "Summit Street", "Clinton Street", "Main Street West", "Magnolia Court", "Heritage Drive", "Old York Road", "Rose Street", "North Street", "Manor Drive", "5th Street West", "Railroad Street", "Dogwood Drive", "Sycamore Drive", "Forest Drive", "Tanglewood Drive", "Spruce Street", "Durham Road", "Edgewood Road", "Adams Avenue", "Warren Avenue", "Cardinal Drive", "Walnut Street", "Amherst Street", "Buttonwood Drive", "Broad Street West", "Garden Street", "Creek Road", "Hillside Drive", "Fieldstone Drive", "York Road", "Eagle Road", "Valley View Drive", "High Street", "Fawn Court", "Route 4", "Myrtle Street", "8th Avenue", "Vine Street", "Chestnut Street", "Hickory Street", "Locust Lane", "Route 30", "Berkshire Drive", "2nd Street North", "8th Street South", "Prospect Avenue", "Virginia Street", "Center Street", "Route 70", "Laurel Lane", "Laurel Street", "West Avenue", "Beech Street", "3rd Street East", "Union Street", "8th Street", "2nd Street East", "Sherman Street", "Cobblestone Court", "Hawthorne Avenue", "Cottage Street", "Jefferson Street", "George Street", "Park Place", "Clark Street"};
    String[] cityString = new String[]{"Vancouver", "Burnaby", "Coquitlam", "Surrey", "Downtown", "Downtown Eastside", "North Vancouver", "South Vancouver", "East Vancouver", "West Vancouver", "Port Moody", "Richmond", "Delta", "Surrey", "Langley"};

    public PopulateBeforeSave(Context context, String name, TextView tv_longitude, TextView tv_latitude) {
        String gender, address, areacode, phone, street, sin, city;
        int shortphoneInt1, shortphoneInt2, sin1, sin2, sin3, suite;
        Random random = new Random();

        // Random age
        int age = random.nextInt(100);

        // Random gender
        if (random.nextInt(2) == 1) gender = "Male";
        else gender = "Female";

        // Random area code
        if (random.nextInt(2) == 1) areacode = "604";
        else areacode = "778";

        // Random phone
        shortphoneInt1 = 99 + random.nextInt(900);
        shortphoneInt2 = 999 + random.nextInt(9000);
        phone = String.valueOf(areacode) + "-" + String.valueOf(shortphoneInt1) + "-" + String.valueOf(shortphoneInt2);

        // SIN
        sin1 = 99 + random.nextInt(900);
        sin2 = 99 + random.nextInt(900);
        sin3 = 99 + random.nextInt(900);
        sin = sin1 + "-" + sin2 + "-" + sin3;

        // Address
        suite = 99 + random.nextInt(900);
        street = RandomStrings(random.nextInt(randomString.length));
        city = CityStrings(random.nextInt(cityString.length));
        address = String.valueOf(suite) + " " + street + ", " + city;

        Firebase.setAndroidContext(context);
        Firebase rootRef = new Firebase("https://dwabit.firebaseio.com/");
        Firebase ref = rootRef.child("UserInfo").child(name);
        UserInfo userInfo = new UserInfo(name, age, gender, phone, sin, address);
        ref.setValue(userInfo);

        LocationLogic locationLogic = new LocationLogic(context);
        locationLogic.locationGetter(name, tv_longitude, tv_latitude);
    }

    class UserInfo {
        String name, gender, address, sin, phone;
        int age;

        public UserInfo(String name, int age, String gender, String phone, String sin, String address) {
            this.name = name;
            this.sin = sin;
            this.gender = gender;
            this.address = address;
            this.phone = phone;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public String getAddress() {
            return address;
        }

        public String getSin() {
            return sin;
        }

        public String getPhone() {
            return phone;
        }

        public int getAge(){
            return age;
        }
    }

    String RandomStrings(int modifier) {
        return randomString[modifier];
    }

    String CityStrings(int modifier){
        return cityString[modifier];
    }
}
