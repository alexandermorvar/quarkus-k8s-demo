package pe.com.serafan.dto.response;

import java.util.List;

public class PageResponse<T> {

    private List<T> content;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    private boolean first;

    private boolean last;

    private boolean hasNext;

    private boolean hasPrevious;

    public PageResponse(
            List<T> content,
            int page,
            int size,
            long totalElements
    ) {

        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;

        this.totalPages = size == 0
                ? 0
                : (int) Math.ceil((double) totalElements / size);

        this.first = page == 0;

        this.last = totalPages == 0 || page >= totalPages - 1;

        this.hasNext = !last;

        this.hasPrevious = !first;
    }

    public List<T> getContent() {
        return content;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }
}