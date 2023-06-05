import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, switchMap } from 'rxjs';
import { Article } from 'src/app/models/article.model';
import { ArticleService } from 'src/app/services/article.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.scss'],
})
export class PostDetailsComponent {
  article$: Observable<Article> = this.route.paramMap.pipe(
    switchMap(params => {
      const articleId = Number(params.get('id'));
      return this.articleService.getArticleById(articleId);
    })
  );

  constructor(
    private articleService: ArticleService,
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  likeArticle(id: number | undefined) {
    if (typeof id === 'undefined') return;
    this.articleService
      .likeArticle(id, this.userService.getUserId())
      .subscribe(response => console.log(response));
  }

  saveArticle(id: number | undefined) {
    if (typeof id === 'undefined') return;
    this.articleService
      .saveArticle(id, this.userService.getUserId())
      .subscribe(response => console.log(response));
  }
}
