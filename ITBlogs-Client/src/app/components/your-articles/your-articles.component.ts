import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from 'src/app/models/article.model';
import { ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-your-articles',
  templateUrl: './your-articles.component.html',
  styleUrls: ['./your-articles.component.scss'],
})
export class YourArticlesComponent implements OnInit {
  articles = new Observable<Article[] | undefined>();

  constructor(private articleService: ArticleService) {}

  ngOnInit(): void {
    this.articles = this.articleService.getYourArticles();
  }
}
