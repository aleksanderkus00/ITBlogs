import { Component, Input } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Article } from 'src/app/models/article.model';
import { ArticleService } from 'src/app/services/article.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss'],
})
export class PostComponent {
  @Input() article!: Article;

  constructor(
    private articleService: ArticleService,
    private userService: UserService,
    private tostrService: ToastrService
  ) {}

  isLiked(): boolean {
    return this.userService.isArticleLiked(this.article.id);
  }

  likeArticle = (): void => {
    if (this.article == undefined || this.article.id == undefined) return;
    this.articleService
      .likeArticle(this.article?.id, this.userService.getUserId())
      .subscribe(res => {
        if (res) {
          this.tostrService.success('Liked successfully!');
          this.article.likes = this.article?.likes + 1 || 1;
        } else this.tostrService.error('Something went wrong...');
      });
  };

  saveArticle = (): void => {
    if (this.article == undefined || this.article.id == undefined) return;
    this.articleService
      .saveArticle(this.article.id, this.userService.getUserId())
      .subscribe(res => {
        if (res) this.tostrService.success('Saved successfully!');
        else this.tostrService.error('Something went wrong...');
      });
  };
}
