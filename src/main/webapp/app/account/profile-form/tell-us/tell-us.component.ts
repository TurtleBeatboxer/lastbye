import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from 'app/core/auth/account.service';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { NgxQrcodeElementTypes, NgxQrcodeErrorCorrectionLevels } from '@techiediaries/ngx-qrcode';
@Component({
  selector: 'jhi-tell-us',
  templateUrl: './tell-us.component.html',
  styleUrls: ['./tell-us.component.scss'],
})
export class TellUsComponent {
  retrievedImage;
  qr;
  elementType = NgxQrcodeElementTypes.URL;
  correctionLevel = NgxQrcodeErrorCorrectionLevels.HIGH;
  constructor(
    private router: Router,
    private accountService: AccountService,
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  redirect() {
    if (this.accountService.userIdentity) {
      this.accountService.userIdentity.levelOfForm = 1;
      this.router.navigate(['user/profile1']);
    }
  }

  getPicture() {}

  buff(buffer) {
    var binary = '';
    var bytes = new Uint8Array(buffer);
    var len = bytes.byteLength;
    for (var i = 0; i < len; i++) {
      binary += String.fromCharCode(bytes[i]);
    }
    return window.btoa(binary);
  }
}
