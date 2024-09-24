<p align="center">
  <a href="https://github.com/pkg-dot-zip/SimplePop3MailClient/" rel="noopener">
 <img width=400px height=400px src="docs/logo.png" alt="Project logo"></a>
</p>

<h3 align="center">SimplePop3MailClient</h3>

<div align="center">

  [![Stars](https://img.shields.io/github/stars/pkg-dot-zip/SimplePop3MailClient.svg)](https://github.com/pkg-dot-zip/SimplePop3MailClient/stargazers)
  [![GitHub Issues](https://img.shields.io/github/issues/pkg-dot-zip/SimplePop3MailClient.svg)](https://github.com/pkg-dot-zip/SimplePop3MailClient/issues)
  [![GitHub Pull Requests](https://img.shields.io/github/issues-pr/pkg-dot-zip/SimplePop3MailClient.svg)](https://github.com/pkg-dot-zip/SimplePop3MailClient/pulls)

</div>

<p align="center">A very simple POP3 client made in <a href="https://kotlinlang.org/">Kotlin</a> for a simple univeristy exercise!
</p>

## 📝 Table of Contents
- [About](#about)
- [Usage](#usage)
- [Built Using](#built_using)
- [Authors](#authors)

## 🧐 About <a name="about"></a>
This repository contains a simple POP3 client built in Kotlin as part of a university assignment. The purpose of the project is to demonstrate socket programming by creating a basic POP3 email client that connects to a mail server, retrieves emails, and displays their subjects on the console.

### What does it do? 🤔
The project establishes a secure SSL connection to a specified POP3 server, authenticates using your credentials, and retrieves the list of emails in your inbox. It then fetches and displays the subject lines of those emails. The server, username, and password are configurable through environment variables for security and flexibility. **It demonstrates how to interact with a POP3 mail server from scratch without using a pre-built POP library.**

### Why?! 😱
This project is a practice assignment designed to improve skills in network socket programming and application protocols. The assignment required us to implement a POP3 client from the ground up, handling connection, authentication, and retrieving email data. This was done without relying on pre-existing POP3 libraries, encouraging us to understand how the protocol works at a deeper level. The added challenge was implementing secure socket communication due to modern providers' requirements for SSL/TLS connections.

This exercise **builds** a solid foundation in network programming and **reinforces** protocol handling skills.

### Features 🌟
- Establish a secure connection to a POP3 server (SSL).
- Authenticate with the provided username and password.
- List and retrieve emails from the server.
- Display the headers (e.g., Subject, From, To, Date) of each email.
- Basic logging to track activity (connected, command sent, response received).

## 🎈 Usage <a name="usage"></a>
1. Clone the repository.
1. Open the project in [IntelliJ](https://www.jetbrains.com/idea/).
1. Create a .env file in the project root directory.
1. Create values for MAIL_USERNAME & MAIL_PASSWORD.
1. Run the `main()` method and enjoy!

## ⛏️ Built Using <a name = "built_using"></a>
- [IntelliJ](https://www.jetbrains.com/idea/) - IDE used
- [Kotlin](https://kotlinlang.org/) - Language used to program in
- [kotlin-logging](https://github.com/oshai/kotlin-logging) - Lightweight Multiplatform logging framework for Kotlin. A convenient and performant logging facade
- [Dotenv](https://github.com/cdimascio/dotenv-kotlin) - Dotenv is a module that loads environment variables from a .env file

## ✍️ Authors <a name = "authors"></a>
- [@OnsPetruske](https://github.com/pkg-dot-zip) - Idea & Initial work.

See also the list of [contributors](https://github.com/pkg-dot-zip/KobwebPortfolioTemplate/contributors) who participated in this project.
