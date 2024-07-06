#!/usr/bin/env python3

import os
import sys
import argparse
import requests
import logging
from PIL import Image, ImageDraw
from requests.auth import HTTPBasicAuth

ICON_MAPPINGS = {
  "72x72"  : "mipmap-hdpi",
  "96x96"  : "mipmap-mdpi",
  "128x128": "mipmap-xhdpi",
  "144x144": "mipmap-xxhdpi",
  "192x192": "mipmap-xxxhdpi"
}

def download_icon(icon_url, fallback_url, output_path, auth=None):
  """
  Download the icon. If the download fails, use the fallback icon.

  :param icon_url: URL to the icon
  :param fallback_url: URL to the fallback icon
  :param output_path: Path to save the downloaded icon
  :param auth: Optional tuple (username, password) for basic authentication
  :return: None
  """
  def _download(url):
    try:
      response = requests.get(url, auth=auth)
      response.raise_for_status()
      return response.content
    except requests.RequestException as e:
      logging.error(f"Failed to download icon from '{url}': {e}")
      return None

  icon_content = _download(icon_url)
  if icon_content is None:
    logging.info(f"Attempting to download fallback icon from '{fallback_url}'...")
    icon_content = _download(fallback_url)
    if icon_content is None:
      logging.error(f"Failed to download fallback icon from '{fallback_url}'")
      sys.exit(1)

  with open(output_path, 'wb') as f:
    f.write(icon_content)
  logging.info(f"Icon downloaded successfully from '{icon_url}'")

def generate_icons(res_directory, icon_path, round=False):
  """
  Generate launcher icons in WEBP format.

  :param res_directory: Path to the resources directory
  :param icon_path: Path to the icon file
  :param round: Whether to generate a round icon (default: False)
  """
  if not os.path.exists(icon_path):
    logging.error(f"Icon file not found: {icon_path}")
    sys.exit(1)
  if not os.path.isdir(res_directory):
    logging.error(f"Invalid resource directory: {res_directory}")
    sys.exit(1)

  for size, directory in ICON_MAPPINGS.items():
    output_icon_directory = os.path.join(res_directory, directory)
    os.makedirs(output_icon_directory, exist_ok=True)
    output_file = "ic_launcher_round.webp" if round else "ic_launcher.webp"
    generate_webp(icon_path, size, os.path.join(output_icon_directory, output_file), round)

  logging.info(f"{'Round ' if round else ''}Launcher icons generated successfully in {res_directory}")

def generate_webp(input_path, size, output_path, round=False):
  """
  Generate a launcher icon in WEBP format.

  :param input_path: Path to the input icon file
  :param size: Size of the icon in format 'widthxheight' (e.g., '72x72')
  :param output_path: Path to save the generated icon
  :param round: Whether to generate a round icon (default: False)
  """
  img = Image.open(input_path)
  img = img.resize((int(size.split('x')[0]), int(size.split('x')[1])), Image.LANCZOS)
  if round:
    bigsize = (img.size[0] * 3, img.size[1] * 3)
    mask = Image.new('L', bigsize, 0)
    draw = ImageDraw.Draw(mask)
    draw.ellipse((0, 0) + bigsize, fill=255)
    mask = mask.resize(img.size, Image.LANCZOS)
    img.putalpha(mask)
  img.save(output_path, 'WEBP', quality=100)

def main():
  logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

  parser = argparse.ArgumentParser(description="Generate launcher icons for Android")
  parser.add_argument('--res-directory', type=str, required=True, help='Path to the resources directory')
  parser.add_argument('--icon-url', type=str, required=True, help='URL to the icon')
  parser.add_argument('--fallback-url', type=str, required=True, help='URL to the fallback icon')
  parser.add_argument('--auth-username', type=str, help='Username for basic authentication')
  parser.add_argument('--auth-password', type=str, help='Password for basic authentication')
  args = parser.parse_args()

  tmp_icon_path = 'launcher-icon@tmp.png'
  try:
    auth = None
    if args.auth_username and args.auth_password:
      auth = HTTPBasicAuth(args.auth_username, args.auth_password)

    download_icon(args.icon_url, args.fallback_url, tmp_icon_path, auth=auth)
    generate_icons(args.res_directory, tmp_icon_path)
    generate_icons(args.res_directory, tmp_icon_path, round=True)
  finally:
    if os.path.exists(tmp_icon_path):
      os.remove(tmp_icon_path)

if __name__ == "__main__":
  main()