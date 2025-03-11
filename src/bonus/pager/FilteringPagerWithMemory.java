package bonus.pager;

import java.util.ArrayList;
import java.util.List;

public class FilteringPagerWithMemory {

    private final int pageSize;

    private int pageNumber = 0;
    public List<String> storage = new ArrayList<>();

    public FilteringPagerWithMemory(SimplePager dataSource, int pageSize) {
        this.pageSize = pageSize;
        for (int i = 0; dataSource.hasPage(i); i++) {
            for (String element : dataSource.getPage(i)) {
                if (element != null) {
                    storage.add(element);
                }
            }
        }
    }

    public boolean hasNextPage() {
        int pageStart = (pageNumber + 1) * pageSize;

        return pageStart < storage.size();
    }

    public boolean hasPreviousPage() {
        return pageNumber > 0;
    }

    public List<String> getNextPage() {
        if (!hasNextPage()) {
            throw new IllegalStateException("there is no next page");
        }

        pageNumber++;

        return getPageCommon();
    }

    public List<String> getCurrentPage() {
        if (pageNumber < 0) {
            throw new IllegalStateException("there is no current page");
        }

        return getPageCommon();
    }

    public List<String> getPreviousPage() {
        if (pageNumber <= 0) {
            throw new IllegalStateException("there is no previous page");
        } else {
            pageNumber--;
        }

        return getPageCommon();
    }

    @Override
    public String toString() {
        return String.format("page: %s", pageNumber);
    }

    private List<String> getPageCommon() {
        int startPos = pageNumber * pageSize;
        int endPos = Math.min(startPos + pageSize, storage.size());

        return storage.subList(startPos, endPos);
    }
}
