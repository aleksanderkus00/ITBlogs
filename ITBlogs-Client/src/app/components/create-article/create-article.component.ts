import { Component } from '@angular/core';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { Article } from 'src/app/models/article.model';
import { ArticleService } from 'src/app/services/article.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss'],
})
export class CreateArticleComponent {
  article: Article = {};
  public Editor = ClassicEditor;

  constructor(
    private articleService: ArticleService,
    private userService: UserService
  ) {}

  public save(): void {
    console.log(this.article);
    this.articleService
      .createArticle(this.article, this.userService.getUserId())
      .subscribe(result => console.log(result));
  }
}
