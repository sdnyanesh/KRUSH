package cs.dal.krush.studentFragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import cs.dal.krush.R;
import cs.dal.krush.models.DBHelper;

/**
 * Sets up the Student Home fragment. This fragment belongs to the StudentMainActivity class
 * and is accessed through the student's bottom navigation bar.
 *
 * Source:
 * [5] List View. (n.d.). Retrieved March 12, 2017,
 * from https://developer.android.com/guide/topics/ui/layout/listview.html
 */
public class StudentHomeFragment extends Fragment {

    private ListView upcomingSessionsListView, tutorsListView;
    private TextView pageTitle, sessionsLabel, bookTutorLabel;
    private DBHelper mydb;
    private Cursor cursorTutorResponse, cursorSessionsResponse;
    private ProfileCursorAdapter profileAdapter;
    private SessionsCursorAdapter sessionsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_home, container, false);

        //get Context:
        Context C = getActivity().getApplicationContext();

        //init DB connection:
        mydb = new DBHelper(C);

        //fetch UI elements:
        upcomingSessionsListView = (ListView)view.findViewById(R.id.upcomingSessionsListView);
        tutorsListView = (ListView)view.findViewById(R.id.availableTutorsListView);
        pageTitle = (TextView)view.findViewById(R.id.titleLabel);
        sessionsLabel = (TextView)view.findViewById(R.id.upcomingSessionsLabel);
        bookTutorLabel = (TextView)view.findViewById(R.id.bookTutorLabel);

        //fetch custom app font:
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(),"fonts/FredokaOne-Regular.ttf");

        //set font style:
        pageTitle.setTypeface(typeFace);
        sessionsLabel.setTypeface(typeFace);
        bookTutorLabel.setTypeface(typeFace);

        //get all student sessions:
        //TODO: Change the ID passed in (1) to the logged in studentId once login has been setup
        cursorSessionsResponse = mydb.tutoringSession.getDataByStudentIdForCursorAdapter(1);

        //set sessions listview adapter:
        sessionsAdapter = new SessionsCursorAdapter(C, cursorSessionsResponse);
        upcomingSessionsListView.setAdapter(sessionsAdapter);

        //get all tutors from DB:
        cursorTutorResponse = mydb.tutor.getAllForCursorAdapter();

        //set tutor's listview adapter:
        profileAdapter = new ProfileCursorAdapter(C, cursorTutorResponse);
        tutorsListView.setAdapter(profileAdapter);

        return view;
    }


}
