import { Component } from '@angular/core';
import { UserSettings } from 'src/app/models/user.settings';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss'],
})
export class SettingsComponent {
  userSettings: UserSettings;

  constructor(private userService: UserService) {
    this.userSettings = {
      id: this.userService.getUserId(),
    };
  }

  updateUser() {
    // TODO: if password change. Check if passwords matches
    this.userService.updateUser(this.userSettings).subscribe(userUpdated => {
      if (userUpdated) this.resetSettingsForm(); // TODO: create pop up that tells if success
    });
  }

  private resetSettingsForm() {
    this.userSettings.username = '';
    this.userSettings.email = '';
    this.userSettings.password = '';
    this.userSettings.repeatedPassword = '';
  }
}
