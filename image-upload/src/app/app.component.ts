import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  constructor(private httpClient: HttpClient) {}

  selectedFile?: File;
  retrievedImage: any;
  base64Data: any;
  retrieveResponse: any;
  message?: string;
  imageName?: string;

  // Get called when the user selectes an image
  public onFileChanged(event: any) {
    // selected file
    this.selectedFile = event.target.files[0];
  }

  // gets called when the user clicks on submit to upload the image
  onUpload() {
    console.log(this.selectedFile);

    // FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests
    const uploadImageData = new FormData();
    if (this.selectedFile) uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

    // Make a call to the Spring Boot Application to save the image
    this.httpClient.post('http://localhost:8080/image/upload', uploadImageData, { observe: 'response' })
      .subscribe(response => {
        this.message = (response.status === 200) ? 'Image upload successfully' : 'Image not upload successfully';
      });
  }

  // gets called when user clicks on retrieve image button yp get the image from backend
  getImage() {
    // make a call to Spring Boot to get the image bytes
    this.httpClient.get('http://localhost:8080/image/get/' + this.imageName)
      .subscribe(response => {
        this.retrieveResponse = response;
        this.base64Data = this.retrieveResponse.picByte;
        this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
      })
  }
}
