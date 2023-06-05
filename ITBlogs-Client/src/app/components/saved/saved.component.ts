import { CdkVirtualScrollViewport } from '@angular/cdk/scrolling';
import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { BehaviorSubject, tap } from 'rxjs';
import { Article } from 'src/app/models/article.model';
import { PaginatedResult } from 'src/app/models/paginated-result.model';
import { ArticleService } from 'src/app/services/article.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-saved',
  templateUrl: './saved.component.html',
  styleUrls: ['./saved.component.scss'],
})
export class SavedComponent implements AfterViewInit {
  @ViewChild(CdkVirtualScrollViewport) viewPort!: CdkVirtualScrollViewport;
  articles = new BehaviorSubject<Article[]>([]);
  currentPage = 0;
  totalPages = 0;
  constructor(
    private articleService: ArticleService,
    private userService: UserService
  ) {}

  ngAfterViewInit() {
    this.viewPort.scrolledIndexChange
      .pipe(
        tap((currentIndex: number) => {
          this.fetchDate(currentIndex);
        })
      )
      .subscribe();
  }

  trackByIdx(i: number) {
    return i;
  }

  private fetchDate(currIndex: number) {
    if (this.currentPage <= this.totalPages) {
      this.articleService
        .getSavedArticles(this.userService.getUserId(), currIndex)
        .subscribe((data: PaginatedResult<Article[]>) => {
          this.articles.next([...this.articles.value, ...data.result]);
          this.currentPage++;
          this.totalPages = data.totalPages;
        });
    }
  }
}
