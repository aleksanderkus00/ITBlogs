import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PostsComponent } from './components/posts/posts.component';
import { NewsComponent } from './components/news/news.component';
import { DiscoverComponent } from './components/discover/discover.component';
import { SettingsComponent } from './components/settings/settings.component';
import { CreateArticleComponent } from './components/create-article/create-article.component';
import { NotFoundComponent } from './components/errors/not-found/not-found.component';
import { PostDetailsComponent } from './components/post-details/post-details.component';
import { SavedComponent } from './components/saved/saved.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/posts',
    pathMatch: 'full',
  },
  { path: 'posts', component: PostsComponent },
  { path: 'post-details/:id', component: PostDetailsComponent },
  { path: 'news', component: NewsComponent },
  { path: 'discover', component: DiscoverComponent },
  { path: 'saved', component: SavedComponent },
  { path: 'settings', component: SettingsComponent },
  { path: 'create-article', component: CreateArticleComponent },
  { path: '**', component: NotFoundComponent, pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
