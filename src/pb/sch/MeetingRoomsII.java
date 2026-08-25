package pb.sch;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MeetingRoomsII {

    public static class Interval {
        int s;
        int e;

        public Interval(int s, int e) {
            this.s = s;
            this.e = e;
        }
    }

    public int minMeetingRooms(Interval[] intervals){
        Arrays.sort(intervals, Comparator.comparingInt(a -> a.s));
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(intervals[0].e);
        for(Interval i : intervals){
            if(!pq.isEmpty() && i.s >= pq.peek()){
                pq.poll();
            }
            pq.add(i.e);
        }
        return pq.size();
    }
}
