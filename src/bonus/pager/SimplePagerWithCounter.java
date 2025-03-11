package bonus.pager;

import java.util.List;

public class SimplePagerWithCounter extends SimplePager {

    private int pageRequestCount = 0;

    public SimplePagerWithCounter(List<String> data, int pageSize) {
        super(data, pageSize);
    }

    @Override
    public List<String> getPage(int pageNumber) {
        pageRequestCount++;
        return super.getPage(pageNumber);
    }

    public int getPageRequestCount() {
        return pageRequestCount;
    }
}
