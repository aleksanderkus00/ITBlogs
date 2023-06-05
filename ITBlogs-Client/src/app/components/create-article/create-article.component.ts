import { Component } from '@angular/core';
import { Route, Router } from '@angular/router';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { ToastrService } from 'ngx-toastr';
import { Article } from 'src/app/models/article.model';
import { ArticleService } from 'src/app/services/article.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss'],
})
export class CreateArticleComponent {
  article: Article = { likes: 0 };
  tags = [
    {
      name: 'News',
      value: 0,
    },
    {
      name: 'Programming',
      value: 1,
    },
    {
      name: 'AI',
      value: 2,
    },
    {
      name: 'Hardware',
      value: 3,
    },
  ];
  selectedValue: any;
  public Editor = ClassicEditor;

  constructor(
    private articleService: ArticleService,
    private userService: UserService,
    private router: Router,
    private toasrService: ToastrService
  ) {}

  public save(): void {
    this.article.category = this.selectedValue;
    this.articleService
      .createArticle(this.article, this.userService.getUserId())
      .subscribe(result => {
        if (result) {
          this.toasrService.show('Successfully created article');
          this.router.navigateByUrl('/your-articles');
        } else {
          this.toasrService.error('Something went wrong...');
        }
      });
  }
}
