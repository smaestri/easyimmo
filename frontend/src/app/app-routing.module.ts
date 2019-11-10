import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NewAdComponent } from './newad/newad.component';
import { ListAdComponent } from './listad/listad.component';



const routes: Routes = [
  { path: 'add-book', component: NewAdComponent},
  { path: '', component: ListAdComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
