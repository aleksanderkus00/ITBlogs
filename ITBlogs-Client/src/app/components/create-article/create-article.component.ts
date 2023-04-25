import { Component } from '@angular/core';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { Article } from 'src/app/models/article.model';

@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss'],
})
export class CreateArticleComponent {
  article: Article = {};
  public Editor = ClassicEditor;

  public save(): void {
    console.log(this.article);
  }
}
