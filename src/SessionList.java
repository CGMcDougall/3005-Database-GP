/*
Container class to hold Session objects
 */
package src;

import java.util.ArrayList;
import java.util.List;

public class SessionList extends ArrayList<Session>{

    public Session getSession(int sessionId) {
        for (Session s : this)
            if (s.getSessionId() == sessionId)
                return s;

        return null;
    }

    public List<Integer> getSessionsIds() {
        List<Integer> sessionsIds = new ArrayList<>();
        for (Session s : this)
            sessionsIds.add(s.getSessionId());

        return sessionsIds;
    }

    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append("Schedule:\n");
        for (Session session : this) s.append(session).append("\n");
        return s.toString();
    }

}
