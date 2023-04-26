import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from 'src/app/models/article.model';
import { ArticleService } from 'src/app/services/article.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-saved',
  templateUrl: './saved.component.html',
  styleUrls: ['./saved.component.scss'],
})
export class SavedComponent implements OnInit {
  articles$ = new Observable<Article[] | undefined>();

  constructor(
    private articleService: ArticleService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.articles$ = this.articleService.getSavedArticles(
      this.userService.getUserId()
    );
  }
}
