import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NgxQrcodeElementTypes, NgxQrcodeErrorCorrectionLevels } from '@techiediaries/ngx-qrcode';
import { AccountService } from 'app/core/auth/account.service';
import { data } from 'cypress/types/jquery';
import { jsPDF } from 'jspdf';
@Component({
  selector: 'jhi-code-qr',
  templateUrl: './code-qr.component.html',
  styleUrls: ['./code-qr.component.scss'],
})
export class CodeQrComponent implements OnInit {
  elementType = NgxQrcodeElementTypes.URL;
  correctionLevel = NgxQrcodeErrorCorrectionLevels.HIGH;
  link;

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountService.selectedData$.subscribe(data => {
      this.link = this.createQrLink(data ? data.codeQr : null);
      this.createPDFfile();
    });
  }

  createQrLink(codeQr: string | null): string {
    if (codeQr) {
      return `localhost:9000/user/accountRecovery/${codeQr}`;
    } else {
      return 'www.google.com';
    }
  }

  createPDFfile() {}

  downloadFile() {
    let doc = new jsPDF();
    const qrcode = document.getElementById('codeQr');
    let imageData = this.getBase64Image(qrcode?.firstChild?.firstChild);
    doc.addImage(imageData, 'JPG', 10, 10, 125, 125);
    doc.text('Here is your code Qr', 40, 10);
    doc.save('FirstPDF.pdf');
  }

  getBase64Image(img) {
    let canvas = document.createElement('canvas');
    canvas.height = img.height;
    canvas.width = img.width;
    let ctx = canvas.getContext('2d');
    ctx?.drawImage(img, 0, 0);
    let dataUerl = canvas.toDataURL('image/png');
    return dataUerl;
  }
}
