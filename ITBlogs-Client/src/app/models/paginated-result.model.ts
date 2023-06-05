export interface PaginatedResult<T> {
  result: T;
  currentPage: number;
  totalPages: number;
}
