<a name="readme-top"></a>
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Workflow][maven-shield]][maven-url]
<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/github_username/repo_name">
    <img src="frontEnd\src\assets\images/logo.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">Proyecto-EI1039-EI1048</h3>

  <p align="center">
    Proyecto EI1039 EI1048, Created by Carlos Feliu, David del Rio and Mónica Pitarch
    <br />
    <a href="https://github.com/ibonek/Proyecto-EI1039-EI1048"><strong>Explore the docs »</strong></a>
    <br />
    <br />
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

This repository presents the joint internship project of the subjects Software Design (EI1039) and Software Paradigms (EI1048) for the academic year 2022/2023. The project covers the competences of both subjects, which complement each other in the realisation of a major software product that is functionally operative, that follows good practices in software design and user interfaces, and that its development rigorously follows the agile ATDD methodology. The project is conceived as a team activity, so it involves all aspects and tasks attributable to the realisation of the project, from communication and consensual decision-making among team members, to the use of tools for collaborative development and software management.

The aim of the project is the development of an application that offers information (news, weather and events) of a city or a geographical point of interest for the user. 

![image](https://user-images.githubusercontent.com/79581464/210437686-e4905b5f-6a22-4d97-987c-0b7749f3b306.png)

The application connects to open public services (free open API) to obtain updated information, and displays it in an orderly fashion. The user can choose the information he/she wants to see at any given moment. 

![image](https://user-images.githubusercontent.com/79581464/210438174-5e7af905-15f0-4a3d-adcf-06850a765b7c.png)

The application allows you to indicate the name (or geographic coordinates) of the city (or place) about which you are interested in obtaining information, and has a certain capacity to configure the system's behaviour (which API to use, what content to show, etc.). 

![image](https://user-images.githubusercontent.com/79581464/210438404-89f0a40a-40ab-4d30-80bf-ad5eceb1d157.png)

The application has a storage system that guarantees the persistence of the information obtained. 

![image](https://user-images.githubusercontent.com/79581464/210438924-595d371e-85cc-4b1d-b0ce-43e0e55dc9a2.png)
![image](https://user-images.githubusercontent.com/79581464/210439510-70e70645-f746-450f-a80b-21616c7ff00c.png)

The business logic of the application is completely decoupled from the user interface, communication protocols and data exchange formats of each service. In addition, as part of the design, the necessary abstractions and patterns are considered to facilitate the efficient implementation of the business logic. The development has been carried out following the agile ATDD methodology, which consists of iterations determined by acceptance tests that promote small and gradual evolutions of the design along the architecture (long skinny slices), in order to progressively overcome these tests. Given that the development is carried out in teams, a teamwork and collaborative development methodology is used to ensure the correct execution of ATDD and the use of collaborative development tools.
<p align="right">(<a href="#readme-top">back to top</a>)</p>


### Built With

* [![Angular][Angular.io]][Angular-url]
* [![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com)
* [![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)](https://www.javascript.com/)
* [![TypeScript](https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white)](https://www.typescriptlang.org/)
* [![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)](https://html.com/)
* [![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)](https://en.wikipedia.org/wiki/CSS)
* [![Firebase](https://img.shields.io/badge/firebase-%23039BE5.svg?style=for-the-badge&logo=firebase)](https://firebase.google.com/)
* [![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/)
* [![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)](https://github.com/features/actions)
<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* npm
  ```sh
  npm install npm@latest -g
  ```

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/ibonek/Proyecto-EI1039-EI1048.git
   ```
2. Install NPM packages
   ```sh
   npm install
   ```
<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_
<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Mónica Pitarch Ruiz - al394883@uji.es

David del Río López - al394756@uji.es

Carlos Feliu Usó    - al394759@uji.es



<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements

* [Proyecto conjunto Curso 2022-2023, versión 1.0, EI1039 - EI1048 Grado en Ingeniería Informática](https://aulavirtual.uji.es/mod/resource/view.php?id=4868114)
* [Angular Guide](https://angular.io/guide/what-is-angular)
* [Building a Web Application with Spring Boot and Angular by Alejandro Ugarte](https://www.baeldung.com/spring-boot-angular-web)
* [Firebase Documentation](https://firebase.google.com/docs/build)
* 
<p align="right">(<a href="#readme-top">back to top</a>)</p>





<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/ibonek/Proyecto-EI1039-EI1048.svg?style=for-the-badge
[contributors-url]: https://github.com/ibonek/Proyecto-EI1039-EI1048/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/ibonek/Proyecto-EI1039-EI1048.svg?style=for-the-badge
[forks-url]: https://github.com/ibonek/Proyecto-EI1039-EI1048/network/members
[stars-shield]: https://img.shields.io/github/stars/ibonek/Proyecto-EI1039-EI1048.svg?style=for-the-badge
[stars-url]: https://github.com/ibonek/Proyecto-EI1039-EI1048/stargazers
[issues-shield]: https://img.shields.io/github/issues/ibonek/Proyecto-EI1039-EI1048.svg?style=for-the-badge
[issues-url]: https://github.com/ibonek/Proyecto-EI1039-EI1048/issues
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/
[maven-shield]: https://img.shields.io/github/actions/workflow/status/ibonek/Proyecto-EI1039-EI1048/maven.yml?branch=master&style=for-the-badge
[maven-url]: https://github.com/ibonek/Proyecto-EI1039-EI1048/actions/workflows/maven.yml
