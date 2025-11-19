#!/bin/bash

# Скрипт для установки CocoaPods

echo "Проверяю наличие CocoaPods..."

# Проверяем, установлен ли CocoaPods
if command -v pod &> /dev/null; then
    POD_PATH=$(which pod)
    echo "CocoaPods уже установлен: $POD_PATH"
    echo "Версия: $(pod --version)"

    # Добавляем путь в local.properties
    echo "kotlin.apple.cocoapods.bin=$POD_PATH" >> local.properties
    echo "Путь к CocoaPods добавлен в local.properties"
    exit 0
fi

echo "CocoaPods не найден. Начинаю установку..."

# Проверяем наличие Homebrew
if command -v brew &> /dev/null; then
    echo "Устанавливаю CocoaPods через Homebrew..."
    brew install cocoapods
elif command -v gem &> /dev/null; then
    echo "Устанавливаю CocoaPods через gem..."
    echo "Попытка установки с sudo (потребуется пароль)..."
    sudo gem install cocoapods

    if [ $? -ne 0 ]; then
        echo "Установка с sudo не удалась. Пробую установить в user scope..."
        gem install cocoapods --user-install

        # Добавляем путь к gem bins в PATH
        if [ -d "$HOME/.gem/ruby" ]; then
            RUBY_VERSION=$(ls -t "$HOME/.gem/ruby" | head -1)
            export PATH="$HOME/.gem/ruby/$RUBY_VERSION/bin:$PATH"
            echo "export PATH=\"\$HOME/.gem/ruby/$RUBY_VERSION/bin:\$PATH\"" >> ~/.zshrc
        fi
    fi
else
    echo "ОШИБКА: Не найдены ни Homebrew, ни gem"
    echo "Пожалуйста, установите CocoaPods вручную:"
    echo "1. Установите Homebrew: https://brew.sh"
    echo "2. Затем выполните: brew install cocoapods"
    exit 1
fi

# Проверяем установку
if command -v pod &> /dev/null; then
    POD_PATH=$(which pod)
    echo "✅ CocoaPods успешно установлен: $POD_PATH"
    echo "Версия: $(pod --version)"

    # Добавляем путь в local.properties
    echo "kotlin.apple.cocoapods.bin=$POD_PATH" >> local.properties
    echo "Путь к CocoaPods добавлен в local.properties"
else
    echo "❌ Установка не удалась"
    exit 1
fi

